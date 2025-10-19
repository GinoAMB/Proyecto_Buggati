package com.integrador.inventario.percistence.tipoMaterial.repository;

import com.integrador.inventario.percistence.tipoMaterial.entity.TipoMaterialEntity;
import org.springframework.data.repository.CrudRepository;

public interface TipoMaterialJpaRepository extends CrudRepository<TipoMaterialEntity, Integer> {
}
