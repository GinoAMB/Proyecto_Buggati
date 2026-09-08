package com.integrador.inventario.web.auth.dto;

public record AuthResponse(
        Integer id,
        String name,
        String role,
        String token,
        Integer idAlmacen
) {}
