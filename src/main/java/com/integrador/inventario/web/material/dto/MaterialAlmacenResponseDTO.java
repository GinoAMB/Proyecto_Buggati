package com.integrador.inventario.web.material.dto;

public record MaterialAlmacenResponseDTO(
        Integer idAlmacen,
        Integer idMaterial,
        Integer idTipo,
        String nombre,
        String descripcion,
        Integer stockMinimo,
        String imagenUrl,
        String publicId,
        Boolean estado,
        String unidadMedida,
        Integer stockActual,
        String nombreAlmacen,   // nuevo
        String nombreUsuario,    // nuevo
        String nombreTipoMaterial
) {}
