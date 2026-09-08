package com.integrador.inventario.persistence.movimiento.adapter;

import com.integrador.inventario.domain.model.Movimiento;
import com.integrador.inventario.domain.repository.MovimientoRepositoryPort;
import com.integrador.inventario.persistence.movimiento.entity.DetalleMovimientoEntity;
import com.integrador.inventario.persistence.movimiento.entity.MovimientoEntity;
import com.integrador.inventario.persistence.movimiento.mapper.DetalleMovimientoMapper;
import com.integrador.inventario.persistence.movimiento.mapper.MovimientoMapper;
import com.integrador.inventario.persistence.movimiento.repository.MovimientoJpaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class MovimientoRepositoryAdapter implements MovimientoRepositoryPort {

    private final MovimientoMapper movimientoMapper;
    private final DetalleMovimientoMapper detalleMovimientoMapper;
    private final MovimientoJpaRepository repository;

    public MovimientoRepositoryAdapter(MovimientoMapper movimientoMapper, DetalleMovimientoMapper detalleMovimientoMapper, MovimientoJpaRepository repository) {
        this.movimientoMapper = movimientoMapper;
        this.detalleMovimientoMapper = detalleMovimientoMapper;
        this.repository = repository;
    }


    @Override
    public Movimiento save(Movimiento movimiento) {
        // 1️⃣ Mapear Domain → Entity
        MovimientoEntity entity = movimientoMapper.toEntity(movimiento);

        // 2️⃣ Asignar el movimiento padre a cada detalle
        if (entity.getDetalles() != null) {
            for (DetalleMovimientoEntity det : entity.getDetalles()) {
                det.setMovimiento(entity); // 🔹 clave para que id_movimiento no sea null
            }
        }

        // 3️⃣ Guardar el movimiento junto con los detalles
        MovimientoEntity saved = repository.save(entity);

        // 4️⃣ Mapear Entity → Domain y devolver
        return movimientoMapper.toDomain(saved);
    }


    @Override
    public Optional<Movimiento> findById(Integer idMovimiento) {
        return repository.findById(idMovimiento)
                .map(movimientoMapper::toDomain);
    }

    @Override
    public List<Movimiento> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(movimientoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Movimiento> findByUsuarioId(Integer idUsuario) {
        return repository.findByUsuarioIdUsuario(idUsuario)
                .stream()
                .map(movimientoMapper::toDomain)
                .toList();
    }

    @Override
    public List<Movimiento> findByAlmacenId(Integer idAlmacen) {
        return repository.findByAlmacenIdAlmacen(idAlmacen)
                .stream()
                .map(movimientoMapper::toDomain)
                .toList();
    }

    @Override
    public long countByFecha(LocalDate fecha) {
        return repository.countByFechaHoraBetween(
                fecha.atStartOfDay(),
                fecha.plusDays(1).atStartOfDay()
        );
    }

    @Override
    public List<Movimiento> findUltimosMovimientos(int cantidad) {
        List<MovimientoEntity> entities = repository.findUltimosMovimientos(PageRequest.of(0, cantidad));
        return entities.stream()
                .map(movimientoMapper::toDomain)
                .toList();
    }
}
