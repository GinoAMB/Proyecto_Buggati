package com.integrador.inventario.domain.exception.almacen;

public class AlmacenNoEcontradoException extends RuntimeException{
    public AlmacenNoEcontradoException(Integer id){
        super("Almacen con id: "+id+" no existe");
    }
}
