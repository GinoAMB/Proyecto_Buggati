package com.integrador.inventario.domain.exception.movimiento;

public class StockInsuficienteException extends RuntimeException {

    public StockInsuficienteException(String nombreMaterial, int stockActual, int solicitado) {
        super("Stock insuficiente para el material '" + nombreMaterial +
                "'. Stock actual: " + stockActual +
                ", solicitado: " + solicitado);
    }
}
