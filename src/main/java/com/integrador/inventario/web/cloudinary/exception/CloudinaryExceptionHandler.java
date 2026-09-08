package com.integrador.inventario.web.cloudinary.exception;

import com.integrador.inventario.domain.exception.cloudinary.ErrorAlEliminarImagenException;
import com.integrador.inventario.domain.exception.cloudinary.ErrorAlSubirImagenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CloudinaryExceptionHandler {

    @ExceptionHandler(ErrorAlSubirImagenException.class)
    public ResponseEntity<Error> handleCloudinaryUpload(ErrorAlSubirImagenException ex) {
        Error error = new Error("cloudinary-upload-error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ErrorAlEliminarImagenException.class)
    public ResponseEntity<Error> handleCloudinaryDelete(ErrorAlEliminarImagenException ex) {
        Error error = new Error("cloudinary-delete-error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    //Cualquier otra excepción no controlada → HTTP 500 (Internal Server Error)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handle(Exception ex){
        Error error = new Error("unknown-error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
