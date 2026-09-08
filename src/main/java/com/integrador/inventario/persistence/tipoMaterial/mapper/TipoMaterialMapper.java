package com.integrador.inventario.persistence.tipoMaterial.mapper;

import com.integrador.inventario.domain.model.TipoMaterial;
import com.integrador.inventario.persistence.tipoMaterial.entity.TipoMaterialEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TipoMaterialMapper {

    // Convertir Entity a Domain
    TipoMaterial toDomain(TipoMaterialEntity entity);

    // Convertir Domain a Entity
    TipoMaterialEntity toEntity(TipoMaterial domain);
}
