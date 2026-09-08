package com.integrador.inventario.domain.exception.material;

public class MaterialDuplicadoException extends RuntimeException{
    public MaterialDuplicadoException(String name) {
        super("Ya existe un material con el nombre: " + name);
    }
}
