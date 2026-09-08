package com.integrador.inventario.persistence.material.mapper;

import com.integrador.inventario.domain.model.Material;
import com.integrador.inventario.persistence.material.entity.MaterialEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MaterialMapper {

    // Convertir Entity a Domain
    Material toDomain(MaterialEntity materialEntity);
    // Convertir Domain a Entity
    MaterialEntity toEntity(Material material);
}
