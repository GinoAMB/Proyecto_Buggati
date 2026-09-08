package com.integrador.inventario.domain.exception.material;

public class MaterialNoEncontradoException extends RuntimeException{
    public MaterialNoEncontradoException(Integer id) {
        super("Tipo de Material con id " + id + " no existe");
    }
}
