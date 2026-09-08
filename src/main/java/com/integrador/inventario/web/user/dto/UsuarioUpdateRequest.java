package com.integrador.inventario.web.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioUpdateRequest(
        @NotNull(message = "El rol es obligatorio")
        Integer idRol,

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
        String nombre,

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "Debe ser un correo válido")
        String correo,

        // Contraseña opcional
        @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
        String password,

        boolean estado,

        String imagenUrl,
        String publicId
) {}
