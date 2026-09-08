package com.integrador.inventario.domain.exception.usuario;

public class UsuarioDuplicadoException extends RuntimeException {
    public UsuarioDuplicadoException(String correo) {
        super("Ya existe un usuario con el correo: " + correo);
    }
}