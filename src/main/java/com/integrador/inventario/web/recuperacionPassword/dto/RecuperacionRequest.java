package com.integrador.inventario.web.recuperacionPassword.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RecuperacionRequest(
        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "Debe ser un correo valido")
        String correo
) {}
