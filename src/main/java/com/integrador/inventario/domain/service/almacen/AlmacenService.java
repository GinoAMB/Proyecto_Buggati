package com.integrador.inventario.domain.service.almacen;

import com.integrador.inventario.domain.model.Almacen;
import com.integrador.inventario.domain.model.DetalleAlmacen;

import java.util.List;
import java.util.Optional;

public interface AlmacenService {
    List<Almacen> getAll();
    Optional<Almacen> getById(Integer id);
    Almacen save(Almacen almacen);
    Almacen update(Integer id, Almacen almacen);
    void updateEstado(Integer id, boolean estado);
    Integer obtenerStockTotal();
    Integer contarMaterialesAgotados();
}
