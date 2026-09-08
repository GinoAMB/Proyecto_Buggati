package com.integrador.inventario.web.almacen.dto;

import java.time.LocalDateTime;
import java.util.List;

public record AlmacenResponseDTO(
        Integer idAlmacen,
        String nombre,
        String codigoAlmacen,
        String direccion,
        String ciudad,
        String provincia,
        String region,
        Integer capacidadMaxima,
        String telefonoContacto,
        Boolean estado,
        LocalDateTime fechaCreacion,
        UsuarioResumenDTO usuario,
        List<DetalleAlmacenResumenDTO> materiales
) {}
