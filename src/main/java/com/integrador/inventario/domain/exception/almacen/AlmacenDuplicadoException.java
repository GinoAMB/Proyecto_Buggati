package com.integrador.inventario.domain.exception.almacen;

public class AlmacenDuplicadoException extends RuntimeException{
    public AlmacenDuplicadoException(String name){
        super("Ya existe un almacen con el nombre: "+name);
    }
}
