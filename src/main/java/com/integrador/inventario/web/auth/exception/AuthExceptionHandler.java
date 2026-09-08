package com.integrador.inventario.web.auth.exception;

import com.integrador.inventario.domain.exception.almacen.AlmacenInactivoException;
import com.integrador.inventario.domain.exception.auth.*;
import com.integrador.inventario.domain.exception.usuario.UsuarioInactivoException;
import com.integrador.inventario.domain.exception.usuario.UsuarioNoEncontradoException;
import com.integrador.inventario.domain.exception.usuario.UsuarioSinAlmacenAsignadoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionHandler {

    // Usuario no registrado → 404
    @ExceptionHandler(UsuarioNoEncontradoException.class)
    public ResponseEntity<Error> handleUsuarioNoEncontrado(UsuarioNoEncontradoException ex) {
        Error error = new Error("user-not-found", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // Usuario inactivo → 403
    @ExceptionHandler(UsuarioInactivoException.class)
    public ResponseEntity<Error> handleUsuarioInactivo(UsuarioInactivoException ex) {
        Error error = new Error("user-inactive", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    // Credenciales inválidas → 401
    @ExceptionHandler(CredencialesInvalidasException.class)
    public ResponseEntity<Error> handleCredencialesInvalidas(CredencialesInvalidasException ex) {
        Error error = new Error("invalid-credentials", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    // Usuario sin almacén asignado → 409
    @ExceptionHandler(UsuarioSinAlmacenAsignadoException.class)
    public ResponseEntity<Error> handleUsuarioSinAlmacen(UsuarioSinAlmacenAsignadoException ex) {
        Error error = new Error("warehouse-not-assigned", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    // Almacén inactivo → 403
    @ExceptionHandler(AlmacenInactivoException.class)
    public ResponseEntity<Error> handleAlmacenInactivo(AlmacenInactivoException ex) {
        Error error = new Error("warehouse-inactive", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    // Cualquier otra excepción no controlada → 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handle(Exception ex) {
        Error error = new Error("unknown-error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
