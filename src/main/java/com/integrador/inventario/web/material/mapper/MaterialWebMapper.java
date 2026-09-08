package com.integrador.inventario.web.material.mapper;

import com.integrador.inventario.domain.model.Material;
import com.integrador.inventario.web.material.dto.MaterialRequest;
import com.integrador.inventario.web.material.dto.MaterialResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MaterialWebMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tipoMaterial.id", source = "tipoMaterialId")
    Material toDomain(MaterialRequest materialRequest);

    @Mapping(target = "tipoMaterialId", source = "tipoMaterial.id")
    @Mapping(target = "tipoMaterialNombre", source = "tipoMaterial.name")
    MaterialResponse toResponseDTO(Material material);
}
