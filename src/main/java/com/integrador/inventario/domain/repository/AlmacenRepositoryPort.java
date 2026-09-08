package com.integrador.inventario.domain.repository;

import com.integrador.inventario.domain.model.Almacen;

import java.util.List;
import java.util.Optional;

public interface AlmacenRepositoryPort {
    List<Almacen> getAll();
    Optional<Almacen> getById(Integer id);
    Almacen save(Almacen almacen);
    Almacen update(Integer id, Almacen almacen);
    void updateEstado(Integer id, boolean estado);
    Optional<Almacen> findByUsuarioId(Integer idUsuario);
}
