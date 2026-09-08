package com.integrador.inventario.persistence.token.mapper;

import com.integrador.inventario.domain.model.TokenRecuperacion;
import com.integrador.inventario.persistence.token.entity.TokenRecuperacionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TokenMapper {

    // De Entity -> Dominio
    TokenRecuperacion toToken(TokenRecuperacionEntity entity);

    // De Dominio -> Entity
    TokenRecuperacionEntity toTokenEntity(TokenRecuperacion token);
}
