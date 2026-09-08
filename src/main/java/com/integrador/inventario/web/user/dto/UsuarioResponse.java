package com.integrador.inventario.web.user.dto;

import java.time.LocalDateTime;

public record UsuarioResponse(
        Integer idUsuario,
         String nombre,
         String correo,
         boolean estado,
         String imagenUrl,
         LocalDateTime fechaCreacion,
         String rolNombre
) {}
