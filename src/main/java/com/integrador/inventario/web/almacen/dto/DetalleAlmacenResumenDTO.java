package com.integrador.inventario.web.almacen.dto;

public record DetalleAlmacenResumenDTO(
        Integer idDetalle,
        String nombreMaterial,
        Integer cantidadDisponible
) {}
