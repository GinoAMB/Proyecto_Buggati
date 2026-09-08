package com.integrador.inventario.domain.repository;

import com.integrador.inventario.domain.model.Movimiento;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MovimientoRepositoryPort {

    Movimiento save(Movimiento movimiento);
    Optional<Movimiento> findById(Integer idMovimiento);
    List<Movimiento> findAll();
    List<Movimiento> findByUsuarioId(Integer idUsuario);
    List<Movimiento> findByAlmacenId(Integer idAlmacen);
    long countByFecha(LocalDate fecha);
    List<Movimiento> findUltimosMovimientos(int cantidad);
}
