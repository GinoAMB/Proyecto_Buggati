package com.integrador.inventario.web.user.mapper;

import com.integrador.inventario.domain.model.Rol;
import com.integrador.inventario.web.user.dto.RolResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RolWedMapper {

    RolResponse toResponseDto(Rol rol);
}
