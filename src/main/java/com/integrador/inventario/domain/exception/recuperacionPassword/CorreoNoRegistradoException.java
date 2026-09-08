package com.integrador.inventario.domain.exception.recuperacionPassword;

public class CorreoNoRegistradoException extends RuntimeException{
    public CorreoNoRegistradoException(String message){
        super(message);
    }
}
