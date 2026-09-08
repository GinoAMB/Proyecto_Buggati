package com.integrador.inventario.persistence.movimiento.repository;

import com.integrador.inventario.persistence.movimiento.entity.MovimientoEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MovimientoJpaRepository extends CrudRepository<MovimientoEntity, Integer> {

    List<MovimientoEntity> findByUsuarioIdUsuario(Integer idUsuario);

    List<MovimientoEntity> findByAlmacenIdAlmacen(Integer idAlmacen);

    long countByFechaHoraBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT m FROM MovimientoEntity m ORDER BY m.fechaHora DESC")
    List<MovimientoEntity> findUltimosMovimientos(org.springframework.data.domain.Pageable pageable);
}
