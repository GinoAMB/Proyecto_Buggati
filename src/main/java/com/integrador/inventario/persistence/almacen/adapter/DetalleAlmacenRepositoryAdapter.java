package com.integrador.inventario.persistence.almacen.adapter;

import com.integrador.inventario.domain.model.DetalleAlmacen;
import com.integrador.inventario.domain.repository.DetalleAlmacenRepositoryPort;
import com.integrador.inventario.persistence.almacen.entity.DetalleAlmacenEntity;
import com.integrador.inventario.persistence.almacen.mapper.DetalleAlmacenMapper;
import com.integrador.inventario.persistence.almacen.repository.DetalleAlmacenJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DetalleAlmacenRepositoryAdapter implements DetalleAlmacenRepositoryPort {

    private final DetalleAlmacenJpaRepository repository;
    private final DetalleAlmacenMapper mapper;

    public DetalleAlmacenRepositoryAdapter(DetalleAlmacenJpaRepository repository, DetalleAlmacenMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<DetalleAlmacen> findByAlmacenAndProducto(Integer idAlmacen, Integer idProducto) {
        return repository.findById_IdAlmacenAndId_IdMaterial(idAlmacen, idProducto)
                .map(mapper::toDomain);
    }

    @Override
    public DetalleAlmacen save(DetalleAlmacen detalleAlmacen) {
        DetalleAlmacenEntity entity = mapper.toEntity(detalleAlmacen);
        DetalleAlmacenEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public DetalleAlmacen update(DetalleAlmacen detalleAlmacen) {
        DetalleAlmacenEntity entity = mapper.toEntity(detalleAlmacen);
        DetalleAlmacenEntity updated = repository.save(entity);
        return mapper.toDomain(updated);
    }

    @Override
    public List<DetalleAlmacen> findByAlmacenId(Integer idAlmacen) {
        return repository.findById_IdAlmacen(idAlmacen)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<DetalleAlmacen> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Integer obtenerStockTotal() {
        return repository.obtenerStockTotal();
    }

    @Override
    public Integer contarMaterialesAgotados() {
        return repository.contarMaterialesAgotados();
    }

}
