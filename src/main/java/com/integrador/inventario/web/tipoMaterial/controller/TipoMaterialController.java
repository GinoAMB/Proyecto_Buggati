package com.integrador.inventario.web.tipoMaterial.controller;

import com.integrador.inventario.domain.model.TipoMaterial;
import com.integrador.inventario.domain.service.tipoMaterial.TipoMaterialService;
import com.integrador.inventario.web.tipoMaterial.dto.TipoMaterialInputDTO;
import com.integrador.inventario.web.tipoMaterial.dto.TipoMaterialOutputDTO;
import com.integrador.inventario.web.tipoMaterial.mapper.TipoMaterialWebMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipoMaterial")
public class TipoMaterialController {

    private final TipoMaterialService service;
    private final TipoMaterialWebMapper mapper;

    public TipoMaterialController(TipoMaterialService service, TipoMaterialWebMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    // ============================================================
    // LISTAR TODOS LOS TIPOS DE MATERIAL
    // ============================================================
    @Operation(
            summary = "Listar todos los tipos de material",
            description = "Permite al administrador obtener la lista de todos los tipos de material registrados. **Rol requerido: ADMINISTRADOR**"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de tipos de material retornada correctamente")
    })
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public ResponseEntity<List<TipoMaterialOutputDTO>> getAll() {
        List<TipoMaterialOutputDTO> result = service.getAll()
                .stream()
                .map(mapper::toOutputDTO)
                .toList();
        return ResponseEntity.ok(result);
    }

    // ============================================================
    // OBTENER TIPO DE MATERIAL POR ID
    // ============================================================
    @Operation(
            summary = "Obtener tipo de material por ID",
            description = "Permite al administrador obtener un tipo de material específico por su ID. **Rol requerido: ADMINISTRADOR**"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tipo de material retornado correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró un tipo de material con el ID proporcionado")
    })
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{id}")
    public ResponseEntity<TipoMaterialOutputDTO> getByID(@PathVariable Integer id) {
        return service.getById(id)
                .map(mapper::toOutputDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ============================================================
    // CREAR NUEVO TIPO DE MATERIAL
    // ============================================================
    @Operation(
            summary = "Crear tipo de material",
            description = "Permite al administrador registrar un nuevo tipo de material. **Rol requerido: ADMINISTRADOR**"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tipo de material creado correctamente")
    })
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<TipoMaterialOutputDTO> create(@Valid @RequestBody TipoMaterialInputDTO inputDTO) {
        TipoMaterial saved = service.save(mapper.toDomain(inputDTO));
        return ResponseEntity.ok(mapper.toOutputDTO(saved));
    }

    // ============================================================
    // ACTUALIZAR TIPO DE MATERIAL EXISTENTE
    // ============================================================
    @Operation(
            summary = "Actualizar tipo de material",
            description = "Permite al administrador actualizar un tipo de material existente. **Rol requerido: ADMINISTRADOR**"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tipo de material actualizado correctamente")
    })
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{id}")
    public ResponseEntity<TipoMaterialOutputDTO> update(@PathVariable Integer id,
                                                        @Valid @RequestBody TipoMaterialInputDTO inputDTO) {
        TipoMaterial update = service.update(id, mapper.toDomain(inputDTO));
        return ResponseEntity.ok(mapper.toOutputDTO(update));
    }

    // ============================================================
    // ELIMINAR TIPO DE MATERIAL
    // ============================================================
    @Operation(
            summary = "Eliminar tipo de material",
            description = "Permite al administrador eliminar un tipo de material existente. **Rol requerido: ADMINISTRADOR**"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Tipo de material eliminado correctamente")
    })
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
