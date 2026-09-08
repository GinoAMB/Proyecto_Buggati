package com.integrador.inventario.web.material.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;

public record MaterialRequest(
        @NotNull(message = "El ID del tipo de material es obligatorio")
        Integer tipoMaterialId,

        @NotBlank(message = "El nombre del material es obligatorio")
        @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
        String nombre,

        @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
        String descripcion,

        @NotNull(message = "El stock mínimo es obligatorio")
        @Min(value = 0, message = "El stock mínimo no puede ser negativo")
        Integer stockMin,

        @Size(max = 255, message = "La URL de la imagen no puede exceder 255 caracteres")
        String imagenUrl,

        @Size(max = 255, message = "El publicId no puede exceder 255 caracteres")
        String publicId,

        @NotNull(message = "El estado es obligatorio")
        Boolean estado,

        @NotBlank(message = "La unidad de medida es obligatoria")
        @Size(max = 50, message = "La unidad de medida no puede exceder 50 caracteres")
        String unidadMedida
) {}
