package com.integrador.inventario.percistence.tipoMaterial.mapper;

import com.integrador.inventario.domain.model.TipoMaterial;
import com.integrador.inventario.percistence.tipoMaterial.entity.TipoMaterialEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TipoMaterialMapper {

    // Convertir Entity a Domain
    TipoMaterial toDomain(TipoMaterialEntity entity);

    // Convertir Domain a Entity
    TipoMaterialEntity toEntity(TipoMaterial domain);
}
