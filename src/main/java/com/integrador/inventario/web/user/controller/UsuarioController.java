package com.integrador.inventario.web.user.controller;

import com.integrador.inventario.domain.model.Usuario;
import com.integrador.inventario.domain.service.user.UsuarioService;
import com.integrador.inventario.web.user.dto.UsuarioRequest;
import com.integrador.inventario.web.user.dto.UsuarioResponse;
import com.integrador.inventario.web.user.dto.UsuarioUpdateRequest;
import com.integrador.inventario.web.user.mapper.UsuarioWebMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuario")
@Tag(name = "Usuarios", description = "Operaciones relacionadas con la gestión de usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioWebMapper mapper;

    public UsuarioController(UsuarioService usuarioService, UsuarioWebMapper mapper) {
        this.usuarioService = usuarioService;
        this.mapper = mapper;
    }

    @Operation(
            summary = "Obtener todos los usuarios",
            description = "Permite a los administradores listar todos los usuarios registrados",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> getAll() {
        List<UsuarioResponse> usuarios = usuarioService.getAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(usuarios);
    }

    @Operation(
            summary = "Obtener todos los usuarios ALMACENEROS",
            description = "Permite a los administradores listar todos los usuarios registrados como ALMACENEROS que no esten asignado a un almacen para el POST y PUT de Almacen",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/user-Almacenero")
    public ResponseEntity<List<UsuarioResponse>> getAllSinAlmacen() {
        List<UsuarioResponse> usuarios = usuarioService.findAlmacenerosSinAlmacen().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(usuarios);
    }

    @Operation(
            summary = "Obtener usuario por ID",
            description = "Permite a los administradores obtener los datos de un usuario específico por su ID",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> getById(@PathVariable Integer id) {
        Usuario usuario = usuarioService.getById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return ResponseEntity.ok(mapper.toResponse(usuario));
    }

    @Operation(
            summary = "Registrar nuevo usuario",
            description = "Permite a los administradores registrar un nuevo usuario en el sistema",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping("/registro")
    public ResponseEntity<UsuarioResponse> create(@Valid @RequestBody UsuarioRequest request) {
        Usuario usuario = mapper.toDomain(request);
        Usuario created = usuarioService.create(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(created));
    }

    @Operation(
            summary = "Actualizar usuario",
            description = "Permite a los administradores actualizar los datos de un usuario existente",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> update(
            @PathVariable Integer id,
            @RequestBody UsuarioUpdateRequest request) {

        Usuario usuario = mapper.toDomain(request); // Mapper adaptado a UsuarioUpdateRequest
        Usuario updated = usuarioService.update(id, usuario);
        return ResponseEntity.ok(mapper.toResponse(updated));
    }


    @Operation(
            summary = "Cambiar estado de usuario",
            description = "Permite a los administradores activar o desactivar un usuario",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Void> cambiarEstado(
            @PathVariable Integer id,
            @RequestParam boolean activo
    ) {
        usuarioService.cambiarEstado(id, activo);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Obtener datos del usuario actual",
            description = "Permite a los administradores y almaceneros obtener los datos del usuario autenticado según su token",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','ALMACENERO')")
    @GetMapping("/me")
    public ResponseEntity<UsuarioResponse> getCurrentUser(HttpServletRequest request){
        Long userId = (Long) request.getAttribute("userId");
        if(userId == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Usuario usuario = usuarioService.getById(userId.intValue()).get();

        return ResponseEntity.ok(mapper.toResponse(usuario));
    }
}
