package com.integrador.inventario.persistence.almacen.repository;

import com.integrador.inventario.persistence.almacen.entity.DetalleAlmacenEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DetalleAlmacenJpaRepository extends CrudRepository<DetalleAlmacenEntity, Integer> {
    Optional<DetalleAlmacenEntity> findById_IdAlmacenAndId_IdMaterial(Integer idAlmacen, Integer idMaterial);
    List<DetalleAlmacenEntity> findById_IdAlmacen(Integer idAlmacen);
    List<DetalleAlmacenEntity> findAll();

    @Query("SELECT SUM(d.stockActual) FROM DetalleAlmacenEntity d")
    Integer obtenerStockTotal();

    @Query("SELECT COUNT(DISTINCT d.material.id) " +
            "FROM DetalleAlmacenEntity d " +
            "WHERE d.stockActual = 0")
    Integer contarMaterialesAgotados();
}
