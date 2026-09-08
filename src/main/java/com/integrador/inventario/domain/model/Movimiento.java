package com.integrador.inventario.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movimiento {

    private Integer idMovimiento;
    private Almacen almacen;           // otro modelo de dominio
    private Usuario usuario;           // modelo de dominio
    private String tipoMovimiento; // enum de dominio
    private LocalDateTime fechaHora;
    private String descripcion;
    private String referencia;

    // Relación con detalles
    private List<DetalleMovimiento> detalles;
}
