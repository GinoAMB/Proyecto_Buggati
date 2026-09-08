package com.integrador.inventario.web.user.mapper;

import com.integrador.inventario.domain.model.Usuario;
import com.integrador.inventario.web.user.dto.UsuarioRequest;
import com.integrador.inventario.web.user.dto.UsuarioResponse;
import com.integrador.inventario.web.user.dto.UsuarioUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioWebMapper {

    @Mapping(target = "rol", expression = "java(new Rol(request.idRol(), null))")
    @Mapping(target = "idUsuario", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    Usuario toDomain(UsuarioRequest request);

    @Mapping(target = "rolNombre", source = "rol.nombreRol")
    UsuarioResponse toResponse(Usuario usuario);

    @Mapping(target = "rol", expression = "java(new Rol(request.idRol(), null))")
    @Mapping(target = "idUsuario", ignore = true) // el ID se asigna en el servicio
    @Mapping(target = "fechaCreacion", ignore = true)
    Usuario toDomain(UsuarioUpdateRequest request);
}