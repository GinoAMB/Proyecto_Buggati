package com.integrador.inventario.persistence.movimiento.mapper;

import com.integrador.inventario.domain.model.DetalleMovimiento;
import com.integrador.inventario.persistence.material.mapper.MaterialMapper;
import com.integrador.inventario.persistence.movimiento.entity.DetalleMovimientoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {MaterialMapper.class})
public interface DetalleMovimientoMapper {

    // Entity → Domain
    @Mappings({
            @Mapping(source = "id", target = "idDetalle"),
            @Mapping(source = "material", target = "material"),
            @Mapping(source = "cantidad", target = "cantidad"),
            @Mapping(target = "movimiento", ignore = true) // 🔹 evita StackOverflow
    })
    DetalleMovimiento toDomain(DetalleMovimientoEntity entity);

    // Domain → Entity
    @Mappings({
            @Mapping(source = "idDetalle", target = "id"),
            @Mapping(source = "material", target = "material"),
            @Mapping(source = "cantidad", target = "cantidad"),
            @Mapping(target = "movimiento", ignore = true) // 🔹 asignar manualmente
    })
    DetalleMovimientoEntity toEntity(DetalleMovimiento domain);
}

