package com.integrador.inventario.persistence.tipoMaterial.repository;

import com.integrador.inventario.persistence.tipoMaterial.entity.TipoMaterialEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TipoMaterialJpaRepository extends CrudRepository<TipoMaterialEntity, Integer> {
    boolean existsByName(String name);
    Optional<TipoMaterialEntity> findByName(String name);
}
