package com.integrador.inventario.domain.repository;

import com.integrador.inventario.domain.model.Material;

import java.util.List;
import java.util.Optional;

public interface MaterialRepositoryPort {
    List<Material> getAll();
    Optional<Material> getById(Integer id);
    Material save(Material material);
    Material update(Integer id, Material material);
    void updateEstado(Integer id, boolean estado);
}
