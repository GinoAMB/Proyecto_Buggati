package com.integrador.inventario.web.material.exception;

import com.integrador.inventario.domain.exception.material.MaterialDuplicadoException;
import com.integrador.inventario.domain.exception.material.MaterialNoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MaterialExceptionHandler {

    // Material duplicado → HTTP 400 (Bad Request)
    @ExceptionHandler(MaterialDuplicadoException.class)
    public ResponseEntity<Error> handleMaterialDuplicado(MaterialDuplicadoException ex) {
        Error error = new Error("duplicated-material", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // Material no encontrado → HTTP 404 (Not Found)
    @ExceptionHandler(MaterialNoEncontradoException.class)
    public ResponseEntity<Error> handleMaterialNoEncontrado(MaterialNoEncontradoException ex) {
        Error error = new Error("material-not-found", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // Cualquier otra excepción no controlada → HTTP 500 (Internal Server Error)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handle(Exception ex) {
        Error error = new Error("unknown-error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
