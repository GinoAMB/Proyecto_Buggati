package com.integrador.inventario.persistence.almacen.repository;

import com.integrador.inventario.persistence.almacen.entity.AlmacenEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AlmacenJpaRepository extends CrudRepository<AlmacenEntity, Integer> {
    Optional<AlmacenEntity> findByUsuario_IdUsuario(Integer idUsuario);
}
