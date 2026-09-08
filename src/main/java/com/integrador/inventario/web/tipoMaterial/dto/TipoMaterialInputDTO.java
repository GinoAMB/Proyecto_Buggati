package com.integrador.inventario.web.tipoMaterial.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TipoMaterialInputDTO(
        @NotBlank(message = "El nombre no puede estar vacío")
        @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
        String name
) {}