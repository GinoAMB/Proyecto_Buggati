package com.integrador.inventario.domain.exception.tipoMaterial;

public class TipoMaterialDuplicadoException extends RuntimeException{
    public TipoMaterialDuplicadoException(String name) {
        super("Ya existe un tipo de material con el nombre: " + name);
    }
}
