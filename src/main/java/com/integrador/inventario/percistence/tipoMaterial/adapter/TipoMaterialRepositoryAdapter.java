package com.integrador.inventario.percistence.tipoMaterial.adapter;

import com.integrador.inventario.domain.model.TipoMaterial;
import com.integrador.inventario.domain.repository.TipoMaterialRepositoryPort;
import com.integrador.inventario.percistence.tipoMaterial.mapper.TipoMaterialMapper;
import com.integrador.inventario.percistence.tipoMaterial.repository.TipoMaterialJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class TipoMaterialRepositoryAdapter implements TipoMaterialRepositoryPort {

    private final TipoMaterialJpaRepository repository;
    private final TipoMaterialMapper mapper;

    public TipoMaterialRepositoryAdapter(TipoMaterialJpaRepository repository, TipoMaterialMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;

    }

    @Override
    public List<TipoMaterial> getAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TipoMaterial> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public TipoMaterial save(TipoMaterial tipoMaterial) {
        return mapper.toDomain(
                repository.save(mapper.toEntity(tipoMaterial))
        );
    }

    @Override
    public TipoMaterial update(Integer id, TipoMaterial tipoMaterial) {
        if(!repository.existsById(id)){
            throw new RuntimeException("Tipo de Materia con id "+id+" no existe");
        }
        tipoMaterial.setId(id);
        return mapper.toDomain(
                repository.save(mapper.toEntity(tipoMaterial))
        );
    }

    @Override
    public void delete(Integer id) {
        if(!repository.existsById(id)){
            throw new RuntimeException("Tipo de Materia con id "+id+" no existe");
        }
        repository.deleteById(id);
    }
}
