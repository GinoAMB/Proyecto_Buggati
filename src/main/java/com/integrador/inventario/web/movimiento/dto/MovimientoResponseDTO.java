package com.integrador.inventario.web.movimiento.dto;

import java.util.List;

public record MovimientoResponseDTO(
        Integer idMovimiento,
        String almacenNombre,
        String usuarioNombre,
        String tipoMovimiento,
        String descripcion,
        String referencia,
        String fechaHora,
        List<DetalleMovimientoResponseDTO> detalles
) {}
