package com.integrador.inventario.web.movimiento.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record DetalleMovimientoDTO(

        @NotNull(message = "El ID del material es obligatorio")
        Integer idMaterial,

        @NotNull(message = "La cantidad es obligatoria")
        @Min(value = 1, message = "La cantidad debe ser mayor a 0")
        Integer cantidad

) {}
