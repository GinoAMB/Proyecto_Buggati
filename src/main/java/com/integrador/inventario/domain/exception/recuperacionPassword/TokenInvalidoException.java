package com.integrador.inventario.domain.exception.recuperacionPassword;

public class TokenInvalidoException extends RuntimeException {
    public TokenInvalidoException(String message) {
        super(message);
    }
}