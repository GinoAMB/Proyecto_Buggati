package com.integrador.inventario.domain.exception.auth;

public class CredencialesInvalidasException extends RuntimeException {

    public CredencialesInvalidasException() {
        super("Las credenciales proporcionadas son incorrectas.");
    }
}
