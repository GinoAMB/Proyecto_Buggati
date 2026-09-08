package com.integrador.inventario.web.almacen.exception;

import com.integrador.inventario.domain.exception.almacen.AlmacenDuplicadoException;
import com.integrador.inventario.domain.exception.almacen.AlmacenNoEcontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AlmacenExceptionHandler {

    // Almacén duplicado → HTTP 400 (Bad Request)
    @ExceptionHandler(AlmacenDuplicadoException.class)
    public ResponseEntity<Error> handleAlmacenDuplicado(AlmacenDuplicadoException ex) {
        Error error = new Error("duplicated-almacen", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // Almacén no encontrado → HTTP 404 (Not Found)
    @ExceptionHandler(AlmacenNoEcontradoException.class)
    public ResponseEntity<Error> handleAlmacenNoEncontrado(AlmacenNoEcontradoException ex) {
        Error error = new Error("almacen-not-found", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // Cualquier otra excepción no controlada → HTTP 500 (Internal Server Error)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handle(Exception ex) {
        Error error = new Error("unknown-error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
