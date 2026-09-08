package com.integrador.inventario.domain.service.auth;

import com.integrador.inventario.web.auth.dto.AuthResponse;

public interface AuthService {
    AuthResponse login(String correo, String password);
}
