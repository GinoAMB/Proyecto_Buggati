package com.integrador.inventario.persistence.almacen.adapter;

import com.integrador.inventario.domain.model.Almacen;
import com.integrador.inventario.domain.repository.AlmacenRepositoryPort;
import com.integrador.inventario.persistence.almacen.mapper.AlmacenMapper;
import com.integrador.inventario.persistence.almacen.repository.AlmacenJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class AlmancenRepositoryAdapter implements AlmacenRepositoryPort {

    private final AlmacenJpaRepository repository;
    private final AlmacenMapper almacenMapper;

    public AlmancenRepositoryAdapter(AlmacenJpaRepository repository, AlmacenMapper almacenMapper) {
        this.repository = repository;
        this.almacenMapper = almacenMapper;
    }

    @Override
    public List<Almacen> getAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(almacenMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Almacen> getById(Integer id) {
        return repository.findById(id)
                .map(almacenMapper::toDomain);
    }

    @Override
    public Almacen save(Almacen almacen) {
        return almacenMapper.toDomain(
                repository.save(almacenMapper.toEntity(almacen))
        );
    }

    @Override
    public Almacen update(Integer id, Almacen almacen) {
        almacen.setIdAlmacen(id);
        return almacenMapper.toDomain(
                repository.save(almacenMapper.toEntity(almacen))
        );
    }

    @Override
    public void updateEstado(Integer id, boolean estado) {
        repository.findById(id).ifPresent(entity -> {
            entity.setEstado(estado);
            repository.save(entity);
        } );
    }

    @Override
    public Optional<Almacen> findByUsuarioId(Integer idUsuario) {
        return repository.findByUsuario_IdUsuario(idUsuario)
                .map(almacenMapper::toDomain);
    }
}
