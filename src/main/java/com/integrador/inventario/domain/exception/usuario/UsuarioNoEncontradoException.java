package com.integrador.inventario.domain.exception.usuario;

public class UsuarioNoEncontradoException extends RuntimeException {

    public UsuarioNoEncontradoException(String correo) {
        super("No se encontró un usuario con el correo: " + correo);
    }

    public UsuarioNoEncontradoException(Integer id) {
        super("No se encontró el usuario con ID: " + id);
    }
}