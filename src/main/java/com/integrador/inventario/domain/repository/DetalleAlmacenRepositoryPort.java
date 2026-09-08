package com.integrador.inventario.domain.repository;

import com.integrador.inventario.domain.model.DetalleAlmacen;

import java.util.List;
import java.util.Optional;

public interface DetalleAlmacenRepositoryPort {
    Optional<DetalleAlmacen> findByAlmacenAndProducto(Integer idAlmacen, Integer idProducto);
    DetalleAlmacen save(DetalleAlmacen detalleAlmacen);
    DetalleAlmacen update(DetalleAlmacen detalleAlmacen);
    List<DetalleAlmacen> findByAlmacenId(Integer idAlmacen);
    List<DetalleAlmacen> findAll();
    Integer obtenerStockTotal();
    Integer contarMaterialesAgotados();
}
