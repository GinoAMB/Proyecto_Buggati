package com.integrador.inventario.persistence.material.adapter;

import com.integrador.inventario.domain.model.Material;
import com.integrador.inventario.domain.repository.MaterialRepositoryPort;
import com.integrador.inventario.persistence.material.mapper.MaterialMapper;
import com.integrador.inventario.persistence.material.repository.MaterialJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class MaterialRepositoryAdapter implements MaterialRepositoryPort {

    private final MaterialJpaRepository materialJpaRepository;
    private final MaterialMapper materialMapper;

    public MaterialRepositoryAdapter(MaterialJpaRepository materialJpaRepository, MaterialMapper materialMapper) {
        this.materialJpaRepository = materialJpaRepository;
        this.materialMapper = materialMapper;
    }

    @Override
    public List<Material> getAll() {
        return StreamSupport.stream(materialJpaRepository.findAll().spliterator(),false)
                .map(materialMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Material> getById(Integer id) {
        return materialJpaRepository.findById(id)
                .map(materialMapper::toDomain);
    }

    @Override
    public Material save(Material material) {
        return materialMapper.toDomain(
                materialJpaRepository.save(materialMapper.toEntity(material))
        );
    }

    @Override
    public Material update(Integer id, Material material) {
        material.setId(id);
        return materialMapper.toDomain(
                materialJpaRepository.save(materialMapper.toEntity(material))
        );
    }

    @Override
    public void updateEstado(Integer id, boolean estado) {
        materialJpaRepository.findById(id).ifPresent(entity -> {
            entity.setEstado(estado);
            materialJpaRepository.save(entity);
        });
    }
}
