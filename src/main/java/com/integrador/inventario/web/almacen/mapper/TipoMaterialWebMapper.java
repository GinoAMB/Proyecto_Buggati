package com.integrador.inventario.web.almacen.mapper;

import com.integrador.inventario.domain.model.TipoMaterial;
import com.integrador.inventario.web.almacen.dto.TipoMaterialInputDTO;
import com.integrador.inventario.web.almacen.dto.TipoMaterialOutputDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TipoMaterialWebMapper {
    // InputDTO -> Domain
    TipoMaterial toDomain(TipoMaterialInputDTO dto);

    // Domain -> OutputDTO
    TipoMaterialOutputDTO toOutputDTO(TipoMaterial tipoMaterial);
}
