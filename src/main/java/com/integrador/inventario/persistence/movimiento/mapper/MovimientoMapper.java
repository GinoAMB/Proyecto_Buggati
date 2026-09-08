package com.integrador.inventario.persistence.movimiento.mapper;

import com.integrador.inventario.domain.model.Movimiento;
import com.integrador.inventario.persistence.almacen.mapper.AlmacenMapper;
import com.integrador.inventario.persistence.movimiento.entity.MovimientoEntity;
import com.integrador.inventario.persistence.movimiento.enums.TipoMovimiento;
import com.integrador.inventario.persistence.user.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(
        componentModel = "spring",
        uses = {AlmacenMapper.class, UserMapper.class, DetalleMovimientoMapper.class} // agregar el mapper de detalles
)
public interface MovimientoMapper {

    // Entity → Domain
    @Mappings({
            @Mapping(source = "id", target = "idMovimiento"),
            @Mapping(source = "almacen", target = "almacen"),
            @Mapping(source = "usuario", target = "usuario"),
            @Mapping(source = "tipoMovimiento", target = "tipoMovimiento"),
            @Mapping(source = "fechaHora", target = "fechaHora"),
            @Mapping(source = "descripcion", target = "descripcion"),
            @Mapping(source = "referencia", target = "referencia"),
            @Mapping(source = "detalles", target = "detalles") // explícito
    })
    Movimiento toDomain(MovimientoEntity entity);

    // Domain → Entity
    @Mappings({
            @Mapping(source = "idMovimiento", target = "id"),
            @Mapping(source = "almacen", target = "almacen"),
            @Mapping(source = "usuario", target = "usuario"),
            @Mapping(source = "tipoMovimiento", target = "tipoMovimiento"),
            @Mapping(source = "fechaHora", target = "fechaHora"),
            @Mapping(source = "descripcion", target = "descripcion"),
            @Mapping(source = "referencia", target = "referencia"),
            @Mapping(source = "detalles", target = "detalles") // explícito
    })
    MovimientoEntity toEntity(Movimiento domain);
}
