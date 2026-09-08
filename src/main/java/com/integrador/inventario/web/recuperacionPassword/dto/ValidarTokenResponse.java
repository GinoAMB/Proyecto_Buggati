package com.integrador.inventario.web.recuperacionPassword.dto;

public record ValidarTokenResponse(
        boolean valido,
        String mensaje
) { }
