package com.integrador.inventario.web.movimiento.exception;

import com.integrador.inventario.domain.exception.movimiento.StockInsuficienteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MovimientoExceptionHandler {

    // Stock insuficiente → HTTP 400 (Bad Request)
    @ExceptionHandler(StockInsuficienteException.class)
    public ResponseEntity<Error> handleStockInsuficiente(StockInsuficienteException ex) {
        Error error = new Error("stock-insuficiente", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // Cualquier otra excepción no controlada → HTTP 500 (Internal Server Error)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handle(Exception ex) {
        Error error = new Error("unknown-error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
