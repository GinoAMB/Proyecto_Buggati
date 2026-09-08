package com.integrador.inventario.domain.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleAlmacen {

    private Almacen almacen;      // referencia al almacén
    private Material material;    // referencia al material
    private Integer stockActual;  // cantidad actual en stock
}
