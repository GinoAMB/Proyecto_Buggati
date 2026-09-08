package com.integrador.inventario.domain.service.recupePassword;

import com.integrador.inventario.domain.model.TokenRecuperacion;

public interface RecuperacionContrasenaService {
    TokenRecuperacion generarToken(String correo);
    boolean validarToken(String token);
    void cambiarPassword(String token, String nuevaContrasena);
}
