package com.integrador.inventario.domain.exception.almacen;

public class AlmacenInactivoException extends RuntimeException {
    public AlmacenInactivoException() {
        super("El almacén asignado al usuario se encuentra inactivo.");
    }
}
