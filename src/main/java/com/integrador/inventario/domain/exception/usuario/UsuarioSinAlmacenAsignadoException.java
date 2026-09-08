package com.integrador.inventario.domain.exception.usuario;

public class UsuarioSinAlmacenAsignadoException extends RuntimeException {
    public UsuarioSinAlmacenAsignadoException() {
        super("El usuario almacenero no tiene un almacén asignado.");
    }
}