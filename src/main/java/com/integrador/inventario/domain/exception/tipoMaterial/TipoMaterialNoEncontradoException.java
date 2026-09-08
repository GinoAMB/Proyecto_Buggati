package com.integrador.inventario.domain.exception.tipoMaterial;

public class TipoMaterialNoEncontradoException extends RuntimeException{
    public TipoMaterialNoEncontradoException(Integer id) {
        super("Tipo de Material con id " + id + " no existe");
    }
}
