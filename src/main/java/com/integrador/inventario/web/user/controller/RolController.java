package com.integrador.inventario.web.user.controller;

import com.integrador.inventario.domain.service.rol.RolServicice;
import com.integrador.inventario.web.user.dto.RolResponse;
import com.integrador.inventario.web.user.dto.UsuarioResponse;
import com.integrador.inventario.web.user.mapper.RolWedMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/roles")
@Tag(name = "Roles", description = "Lista de roles")
public class RolController {

    private final RolServicice servicice;
    private final RolWedMapper mapper;

    public RolController(RolServicice servicice, RolWedMapper mapper) {
        this.servicice = servicice;
        this.mapper = mapper;
    }

    @Operation(
            summary = "Obtener los roles",
            description = "Permite a los administradores obtener los roles",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping
    public ResponseEntity<List<RolResponse>> getAll() {
        List<RolResponse> roles = servicice.findAll().stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(roles);
    }
}
