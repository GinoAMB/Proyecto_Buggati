package com.integrador.inventario.web.tipoMaterial.exception;

import com.integrador.inventario.domain.exception.tipoMaterial.TipoMaterialDuplicadoException;
import com.integrador.inventario.domain.exception.tipoMaterial.TipoMaterialNoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TipoMaterialExceptionHandler {

    // Tipo de material duplicado → HTTP 400 (Bad Request)
    @ExceptionHandler(TipoMaterialDuplicadoException.class)
    public ResponseEntity<Error> handleTipoMaterialDuplicado(TipoMaterialDuplicadoException ex) {
        Error error = new Error("duplicated-tipo-material", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // Tipo de material no encontrado → HTTP 404 (Not Found)
    @ExceptionHandler(TipoMaterialNoEncontradoException.class)
    public ResponseEntity<Error> handleTipoMaterialNoEncontrado(TipoMaterialNoEncontradoException ex) {
        Error error = new Error("tipo-material-not-found", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    //Cualquier otra excepción no controlada → HTTP 500 (Internal Server Error)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handle(Exception ex){
        Error error = new Error("unknown-error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
