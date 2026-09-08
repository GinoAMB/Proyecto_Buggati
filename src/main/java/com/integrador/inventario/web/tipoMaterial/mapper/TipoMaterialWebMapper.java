package com.integrador.inventario.web.tipoMaterial.mapper;

import com.integrador.inventario.domain.model.TipoMaterial;
import com.integrador.inventario.web.tipoMaterial.dto.TipoMaterialInputDTO;
import com.integrador.inventario.web.tipoMaterial.dto.TipoMaterialOutputDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TipoMaterialWebMapper {
    // InputDTO -> Domain
    @Mapping(target = "id", ignore = true)
    TipoMaterial toDomain(TipoMaterialInputDTO dto);

    // Domain -> OutputDTO
    TipoMaterialOutputDTO toOutputDTO(TipoMaterial tipoMaterial);
}
