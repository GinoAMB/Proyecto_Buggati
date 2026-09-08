package com.integrador.inventario.web.material.dto;

public record MaterialResponse(
        Integer id,
        Integer tipoMaterialId,
        String tipoMaterialNombre,
        String nombre,
        String descripcion,
        String stockMin,
        String imagenUrl,
        String publicId,
        Boolean estado,
        String unidadMedida
) {}
