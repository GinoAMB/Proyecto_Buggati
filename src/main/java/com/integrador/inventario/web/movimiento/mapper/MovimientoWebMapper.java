package com.integrador.inventario.web.movimiento.mapper;

import com.integrador.inventario.domain.model.*;
import com.integrador.inventario.web.movimiento.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovimientoWebMapper {

    // ===============================
    //      ENTRADA → DOMAIN
    // ===============================
    @Mapping(target = "idMovimiento", ignore = true)
    @Mapping(target = "tipoMovimiento", constant = "ENTRADA")
    @Mapping(target = "fechaHora", ignore = true)
    @Mapping(target = "almacen", ignore = true) // se asignará manualmente
    @Mapping(target = "usuario", ignore = true) // se asignará manualmente
    @Mapping(target = "detalles", expression = "java(toDomainDetalles(dto.detalles()))")
    Movimiento toDomainEntrada(MovimientoEntradaDTO dto);

    // Sobrecarga que recibe ids del token
    default Movimiento toDomainEntrada(MovimientoEntradaDTO dto, Integer idAlmacen, Integer idUsuario) {
        Movimiento mov = toDomainEntrada(dto);
        mov.setAlmacen(new Almacen(idAlmacen));
        mov.setUsuario(new Usuario(idUsuario));
        return mov;
    }

    // ===============================
    //      SALIDA → DOMAIN
    // ===============================
    @Mapping(target = "idMovimiento", ignore = true)
    @Mapping(target = "tipoMovimiento", constant = "SALIDA")
    @Mapping(target = "fechaHora", ignore = true)
    @Mapping(target = "almacen", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "detalles", expression = "java(toDomainDetalles(dto.detalles()))")
    Movimiento toDomainSalida(MovimientoEntradaDTO dto);

    default Movimiento toDomainSalida(MovimientoEntradaDTO dto, Integer idAlmacen, Integer idUsuario) {
        Movimiento mov = toDomainSalida(dto);
        mov.setAlmacen(new Almacen(idAlmacen));
        mov.setUsuario(new Usuario(idUsuario));
        return mov;
    }

    // ===============================
    //    DOMAIN → RESPONSE DTO
    // ===============================
    @Mapping(target = "idMovimiento", source = "idMovimiento")
    @Mapping(target = "almacenNombre", source = "almacen.nombre")
    @Mapping(target = "usuarioNombre", source = "usuario.nombre")
    @Mapping(target = "detalles", source = "detalles")
    @Mapping(target = "fechaHora",
            expression = "java(movimiento.getFechaHora() != null ? movimiento.getFechaHora().toString() : null)")
    MovimientoResponseDTO toResponse(Movimiento movimiento);

    // ===============================
    //   LISTA DETALLE → RESPONSE LIST
    // ===============================
    @Mapping(target = "materialNombre", source = "material.nombre")
    List<DetalleMovimientoResponseDTO> toResponseDetalleList(List<DetalleMovimiento> detalles);

    // ===============================
    //   DETALLE DOMAIN → RESPONSE
    // ===============================
    @Mapping(target = "materialNombre", source = "material.nombre")
    DetalleMovimientoResponseDTO toResponseDetalle(DetalleMovimiento det);

    // ===============================
    //   AUXILIAR DTO → DOMAIN
    // ===============================
    default List<DetalleMovimiento> toDomainDetalles(List<DetalleMovimientoDTO> detalles) {
        return detalles.stream()
                .map(det -> new DetalleMovimiento(
                        null,
                        null,
                        new Material(det.idMaterial()),
                        det.cantidad()
                ))
                .toList();
    }
}

