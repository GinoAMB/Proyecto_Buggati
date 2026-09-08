package com.integrador.inventario.domain.exception.recuperacionPassword;

public class TokenExpiradoException extends RuntimeException {
    public TokenExpiradoException(String message) {
        super(message);
    }
}