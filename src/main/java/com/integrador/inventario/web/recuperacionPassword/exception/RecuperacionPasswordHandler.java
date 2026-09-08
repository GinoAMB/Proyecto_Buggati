package com.integrador.inventario.web.recuperacionPassword.exception;


import com.integrador.inventario.domain.exception.recuperacionPassword.CorreoNoRegistradoException;
import com.integrador.inventario.domain.exception.recuperacionPassword.TokenExpiradoException;
import com.integrador.inventario.domain.exception.recuperacionPassword.TokenInvalidoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RecuperacionPasswordHandler {

    @ExceptionHandler(CorreoNoRegistradoException.class)
    public ResponseEntity<Error> handleCorreoNoRegistrado(CorreoNoRegistradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Error("correo-no-registrado", ex.getMessage()));
    }

    @ExceptionHandler(TokenInvalidoException.class)
    public ResponseEntity<Error> handleTokenInvalido(TokenInvalidoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Error("token-invalido", ex.getMessage()));
    }

    @ExceptionHandler(TokenExpiradoException.class)
    public ResponseEntity<Error> handleTokenExpirado(TokenExpiradoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Error("token-expirado", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleUnknown(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error("error-desconocido", ex.getMessage()));
    }
}
