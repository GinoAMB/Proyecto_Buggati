package com.integrador.inventario.persistence.user.mapper;


import com.integrador.inventario.domain.model.Rol;
import com.integrador.inventario.persistence.user.entity.RolEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RolMapper {

    // De Entity → Dominio
    Rol toRol(RolEntity entity);

    // De Dominio → Entity
    RolEntity toRolEntity(Rol rol);
}
