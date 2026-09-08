package com.integrador.inventario.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetalleMovimiento {

    private Integer idDetalle;
    private Movimiento movimiento;
    private Material material; // modelo de dominio
    private Integer cantidad;
    
}
