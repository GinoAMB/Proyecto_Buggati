package com.integrador.inventario.persistence.almacen.mapper;


import com.integrador.inventario.domain.model.Almacen;
import com.integrador.inventario.persistence.almacen.entity.AlmacenEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AlmacenMapper {

    // Convertir Entity a Domain
    Almacen toDomain(AlmacenEntity almacenEntity);
    // Convertir Domain a Entity
    AlmacenEntity toEntity(Almacen almacen);

}
