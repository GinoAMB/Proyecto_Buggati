package com.integrador.inventario.domain.service.material;

import com.integrador.inventario.domain.model.DetalleAlmacen;
import com.integrador.inventario.domain.model.Material;

import java.util.List;
import java.util.Optional;

public interface MaterialService {
    List<Material> getAll();
    Optional<Material> getById(Integer id);
    Material save(Material material);
    Material update(Integer id, Material material);
    void cambiarEstado(Integer id, boolean estado);
    List<DetalleAlmacen> getMaterialesPorAlmacen(Integer idAlmacen);
    List<DetalleAlmacen> findAll();
 }
