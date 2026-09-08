package com.integrador.inventario.web.auth.controller;

import com.integrador.inventario.domain.service.auth.AuthService;
import com.integrador.inventario.web.auth.dto.AuthResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(
            summary = "Login de usuario",
            description = "Permite autenticar a un usuario usando su correo y contraseña. Devuelve un JWT en caso de éxito."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autenticación exitosa, token generado"),
            @ApiResponse(responseCode = "401", description = "Credenciales incorrectas"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestParam String email,
            @RequestParam String password
    ) {
        AuthResponse response = authService.login(email, password);
        return ResponseEntity.ok(response);
    }
}
