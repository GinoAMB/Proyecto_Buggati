package com.integrador.inventario.domain.exception.cloudinary;

public class ErrorAlSubirImagenException extends RuntimeException {
    public ErrorAlSubirImagenException(String message) {
        super("Error al subir imagen: " + message);
    }
}