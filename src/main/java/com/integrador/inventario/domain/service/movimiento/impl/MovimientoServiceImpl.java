package com.integrador.inventario.domain.service.movimiento.impl;

import com.integrador.inventario.domain.exception.movimiento.StockInsuficienteException;
import com.integrador.inventario.domain.model.DetalleAlmacen;
import com.integrador.inventario.domain.model.Movimiento;
import com.integrador.inventario.domain.model.DetalleMovimiento;
import com.integrador.inventario.domain.repository.DetalleAlmacenRepositoryPort;
import com.integrador.inventario.domain.repository.MovimientoRepositoryPort;
import com.integrador.inventario.domain.service.movimiento.MovimientoService;
import com.integrador.inventario.persistence.movimiento.enums.TipoMovimiento;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoRepositoryPort movimientoRepositoryPort;
    private final DetalleAlmacenRepositoryPort detalleAlmacenRepositoryPort;

    public MovimientoServiceImpl(
            MovimientoRepositoryPort movimientoRepositoryPort,
            DetalleAlmacenRepositoryPort detalleAlmacenRepositoryPort
    ) {
        this.movimientoRepositoryPort = movimientoRepositoryPort;
        this.detalleAlmacenRepositoryPort = detalleAlmacenRepositoryPort;
    }

    // ======================================================
    // ================== REGISTRAR ENTRADA ==================
    // ======================================================
    @Override
    public Movimiento registrarEntrada(Movimiento movimiento) {

        movimiento.setTipoMovimiento("ENTRADA");

        return registrarMovimientoInterno(movimiento, TipoMovimiento.ENTRADA);
    }

    // ======================================================
    // ================== REGISTRAR SALIDA ===================
    // ======================================================
    @Override
    public Movimiento registrarSalida(Movimiento movimiento) {

        movimiento.setTipoMovimiento("SALIDA");

        return registrarMovimientoInterno(movimiento, TipoMovimiento.SALIDA);
    }

    // ======================================================
    // =========== LÓGICA INTERNA COMPARTIDA =================
    // ======================================================
    private Movimiento registrarMovimientoInterno(Movimiento movimiento, TipoMovimiento tipo) {

        // 1. Asignar tipo de movimiento
        movimiento.setTipoMovimiento(tipo.name());

        // 2. Asignar el movimiento padre a cada detalle
        if (movimiento.getDetalles() != null) {
            for (DetalleMovimiento det : movimiento.getDetalles()) {
                det.setMovimiento(movimiento); // evita StackOverflow en MapStruct
            }
        }

        // 3. Guardar el movimiento junto con sus detalles (CascadeType.ALL)
        Movimiento movGuardado = movimientoRepositoryPort.save(movimiento);

        // 4. Actualizar stock según el tipo de movimiento
        if (movGuardado.getDetalles() != null) {
            for (DetalleMovimiento det : movGuardado.getDetalles()) {

                Integer idAlmacen = movGuardado.getAlmacen().getIdAlmacen();
                Integer idMaterial = det.getMaterial().getId();

                // Buscar el stock existente
                Optional<DetalleAlmacen> optDetalleAlmacen =
                        detalleAlmacenRepositoryPort.findByAlmacenAndProducto(idAlmacen, idMaterial);

                // Si no existe, crear nuevo registro de stock
                DetalleAlmacen detAlmacen = optDetalleAlmacen.orElseGet(() -> {
                    DetalleAlmacen da = new DetalleAlmacen();
                    da.setAlmacen(movGuardado.getAlmacen());
                    da.setMaterial(det.getMaterial());
                    da.setStockActual(0); // inicializar en 0
                    return da;
                });

                // 5. Ajustar stock según tipo de movimiento
                int stockActual = detAlmacen.getStockActual();
                if (tipo == TipoMovimiento.ENTRADA) {
                    detAlmacen.setStockActual(stockActual + det.getCantidad());
                } else if (tipo == TipoMovimiento.SALIDA) {
                    int nuevoStock = stockActual - det.getCantidad();
                    if (nuevoStock < 0) {
                        throw new StockInsuficienteException(
                                det.getMaterial().getNombre(),
                                stockActual,
                                det.getCantidad()
                        );
                    }
                    detAlmacen.setStockActual(nuevoStock);
                }

                // 6. Guardar stock actualizado en la base de datos
                detalleAlmacenRepositoryPort.save(detAlmacen);
            }
        }

        return movGuardado;
    }




    // ======================================================
    // ==================== CONSULTAS =======================
    // ======================================================

    @Override
    public Optional<Movimiento> obtenerPorId(Integer idMovimiento) {
        return movimientoRepositoryPort.findById(idMovimiento);
    }

    @Override
    public List<Movimiento> listarTodos() {
        return movimientoRepositoryPort.findAll();
    }

    @Override
    public List<Movimiento> listarPorUsuario(Integer idUsuario) {
        return movimientoRepositoryPort.findByUsuarioId(idUsuario);
    }

    @Override
    public List<Movimiento> listarPorAlmacen(Integer idAlmacen) {
        return movimientoRepositoryPort.findByAlmacenId(idAlmacen);
    }

    @Override
    public long contarMovimientosHoy() {
        LocalDate hoy = LocalDate.now();
        return movimientoRepositoryPort.countByFecha(hoy);
    }

    @Override
    public List<Movimiento> obtenerUltimosMovimientos(int cantidad) {
        return movimientoRepositoryPort.findUltimosMovimientos(cantidad);
    }
}
