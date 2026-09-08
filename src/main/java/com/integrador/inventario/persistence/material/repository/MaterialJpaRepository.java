package com.integrador.inventario.persistence.material.repository;

import com.integrador.inventario.persistence.material.entity.MaterialEntity;
import org.springframework.data.repository.CrudRepository;

public interface MaterialJpaRepository extends CrudRepository<MaterialEntity, Integer> {
}
