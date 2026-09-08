package com.integrador.inventario.domain.service.movimiento;

import com.integrador.inventario.domain.model.Movimiento;

import java.util.List;
import java.util.Optional;

public interface MovimientoService {

    Movimiento registrarEntrada(Movimiento movimiento);
    Movimiento registrarSalida(Movimiento movimiento);
    Optional<Movimiento> obtenerPorId(Integer idMovimiento);
    List<Movimiento> listarTodos();
    List<Movimiento> listarPorUsuario(Integer idUsuario);
    List<Movimiento> listarPorAlmacen(Integer idAlmacen);
    long contarMovimientosHoy();
    List<Movimiento> obtenerUltimosMovimientos(int cantidad);
}
