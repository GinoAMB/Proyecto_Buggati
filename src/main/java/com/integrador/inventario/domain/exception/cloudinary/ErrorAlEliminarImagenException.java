package com.integrador.inventario.domain.exception.cloudinary;

public class ErrorAlEliminarImagenException extends RuntimeException {
    public ErrorAlEliminarImagenException(String message) {
        super("Error al eliminar imagen: " + message);
    }
}
