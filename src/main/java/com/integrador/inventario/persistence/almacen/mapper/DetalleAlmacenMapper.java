package com.integrador.inventario.persistence.almacen.mapper;

import com.integrador.inventario.domain.model.DetalleAlmacen;
import com.integrador.inventario.persistence.almacen.entity.DetalleAlmacenEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DetalleAlmacenMapper {

    // Domain → Entity
    @Mapping(target = "id", expression = "java(new com.integrador.inventario.persistence.almacen.entity.DetalleAlmacenEntity.DetalleAlmacenId(da.getAlmacen().getIdAlmacen(), da.getMaterial().getId()))")
    DetalleAlmacenEntity toEntity(DetalleAlmacen da);

    // Entity → Domain
    DetalleAlmacen toDomain(DetalleAlmacenEntity entity);
}

