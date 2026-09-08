package com.integrador.inventario.web.recuperacionPassword.controller;

import com.integrador.inventario.domain.service.recupePassword.RecuperacionContrasenaService;
import com.integrador.inventario.web.recuperacionPassword.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recuperacion-password")
public class RecuperacionPasswordController {

    private final RecuperacionContrasenaService recuperacionContrasenaService;

    public RecuperacionPasswordController(RecuperacionContrasenaService recuperacionContrasenaService) {
        this.recuperacionContrasenaService = recuperacionContrasenaService;
    }

    // ============================================================
    //                 SOLICITAR TOKEN DE RECUPERACIÓN
    // ============================================================
    @Operation(summary = "Solicitar token de recuperación", description = "Permite a cualquier usuario solicitar un token para recuperar su contraseña enviando su correo electrónico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Se ha enviado un enlace de recuperación al correo proporcionado")
    })
    @PostMapping("/solicitar")
    public ResponseEntity<TokenResponse> solicitarToken(@Valid @RequestBody RecuperacionRequest recuperacionRequest){
        recuperacionContrasenaService.generarToken(recuperacionRequest.correo());
        return ResponseEntity.ok(new TokenResponse("Se ha enviado un enlace de recuperación a tu correo electrónico."));
    }

    // ============================================================
    //                     VALIDAR TOKEN
    // ============================================================
    @Operation(summary = "Validar token de recuperación", description = "Permite validar si un token de recuperación enviado al correo es válido o ha expirado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retorna si el token es válido o no, junto con un mensaje explicativo")
    })
    @PostMapping("/validar")
    public ResponseEntity<ValidarTokenResponse> validarToken(@Valid @RequestBody ValidarTokenRequest validarTokenRequest){
        boolean valido = recuperacionContrasenaService.validarToken(validarTokenRequest.token());
        String mensaje = valido ? "Token válido." : "El token es inválido o ha expirado.";
        return ResponseEntity.ok(new ValidarTokenResponse(valido, mensaje));
    }

    // ============================================================
    //                     CAMBIAR CONTRASEÑA
    // ============================================================
    @Operation(summary = "Cambiar contraseña", description = "Permite cambiar la contraseña utilizando un token de recuperación válido")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "La contraseña ha sido cambiada exitosamente")
    })
    @PostMapping("/cambiar")
    public ResponseEntity<TokenResponse> cambiarPassword(@Valid @RequestBody CambioPasswordRequest cambioPasswordRequest){
        recuperacionContrasenaService.cambiarPassword(cambioPasswordRequest.token(), cambioPasswordRequest.nuevoPassword());
        return ResponseEntity.ok(new TokenResponse("Tu contraseña ha sido cambiada exitosamente."));
    }
}
