package com.integrador.inventario.persistence.user.repository;

import com.integrador.inventario.persistence.user.entity.RolEntity;
import org.springframework.data.repository.CrudRepository;

public interface RolJpaRepository extends CrudRepository<RolEntity, Integer> {
}
