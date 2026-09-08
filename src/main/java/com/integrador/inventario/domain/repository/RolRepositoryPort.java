package com.integrador.inventario.domain.repository;

import com.integrador.inventario.domain.model.Rol;

import java.util.List;

public interface RolRepositoryPort {
    List<Rol> findAll();
}
