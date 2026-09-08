package com.integrador.inventario.domain.exception.usuario;

public class UsuarioInactivoException extends RuntimeException {
    public UsuarioInactivoException() {
        super("El usuario se encuentra inactivo. Comuníquese con el administrador.");
    }
}
