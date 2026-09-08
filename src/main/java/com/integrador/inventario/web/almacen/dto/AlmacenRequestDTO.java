package com.integrador.inventario.web.almacen.dto;

import jakarta.validation.constraints.*;

public record AlmacenRequestDTO(
        @NotNull(message = "El id del usuario es obligatorio")
        Integer idUsuario,

        @NotBlank(message = "El código del almacén es obligatorio")
        @Size(max = 20, message = "El código no debe superar los 20 caracteres")
        String codigoAlmacen,

        @NotBlank(message = "El nombre del almacén es obligatorio")
        @Size(max = 100, message = "El nombre no debe superar los 100 caracteres")
        String nombre,

        @NotBlank(message = "La dirección es obligatoria")
        @Size(max = 150, message = "La dirección no debe superar los 150 caracteres")
        String direccion,

        @NotBlank(message = "La ciudad es obligatoria")
        String ciudad,

        @NotBlank(message = "La provincia es obligatoria")
        String provincia,

        @NotBlank(message = "La región es obligatoria")
        String region,

        @NotNull(message = "La capacidad máxima es obligatoria")
        @Positive(message = "La capacidad debe ser mayor a cero")
        Integer capacidadMaxima,

        @Pattern(regexp = "^(\\+\\d{1,3})?\\s?\\d{6,15}$", message = "El teléfono debe tener un formato válido")
        String telefonoContacto,

        Boolean estado

) {}