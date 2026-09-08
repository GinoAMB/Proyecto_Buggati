package com.integrador.inventario.web.movimiento.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record MovimientoEntradaDTO(

        @NotNull(message = "La descripción es obligatoria")
        @Size(min = 3, max = 255, message = "La descripción debe tener entre 3 y 255 caracteres")
        String descripcion,

        String referencia,

        @NotEmpty(message = "Debe incluir al menos un detalle de material")
        @Valid
        List<DetalleMovimientoDTO> detalles

) {}
