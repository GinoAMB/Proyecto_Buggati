package com.integrador.inventario.persistence.user.mapper;

import com.integrador.inventario.domain.model.Usuario;
import com.integrador.inventario.persistence.user.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // De Entity → Dominio
    Usuario toUsuario(UserEntity entity);

    // De Dominio → Entity
    UserEntity toUserEntity(Usuario usuario);

}
