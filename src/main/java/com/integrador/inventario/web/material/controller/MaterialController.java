package com.integrador.inventario.web.material.controller;

import com.integrador.inventario.domain.model.DetalleAlmacen;
import com.integrador.inventario.domain.model.Material;
import com.integrador.inventario.domain.service.material.MaterialService;
import com.integrador.inventario.web.almacen.mapper.AlmacenWepMapper;
import com.integrador.inventario.web.material.dto.MaterialAlmacenResponseDTO;
import com.integrador.inventario.web.material.dto.MaterialRequest;
import com.integrador.inventario.web.material.dto.MaterialResponse;
import com.integrador.inventario.web.material.mapper.MaterialWebMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/material")
public class MaterialController {

    private final MaterialService service;
    private final MaterialWebMapper mapper;
    private final AlmacenWepMapper almacenWepMapper;

    public MaterialController(MaterialService service, MaterialWebMapper mapper, AlmacenWepMapper almacenWepMapper) {
        this.service = service;
        this.mapper = mapper;
        this.almacenWepMapper = almacenWepMapper;
    }

    @Operation(summary = "Listar todos los materiales", description = "Permite al ADMINISTRADOR y ALMACENERO listar todos los materiales del sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de materiales obtenida correctamente"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'ALMACENERO')")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public ResponseEntity<List<MaterialResponse>> getAll(){
        List<MaterialResponse> result = service.getAll()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Obtener un material por ID", description = "Permite al ADMINISTRADOR obtener un material específico mediante su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Material encontrado"),
            @ApiResponse(responseCode = "404", description = "Material no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{id}")
    public ResponseEntity<MaterialResponse> getById(@PathVariable Integer id){
        return service.getById(id)
                .map(mapper::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un material", description = "Permite al ADMINISTRADOR crear un nuevo material")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Material creado correctamente"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<MaterialResponse> create(@Valid @RequestBody MaterialRequest materialRequest){
        Material saved = service.save(mapper.toDomain(materialRequest));
        return ResponseEntity.ok(mapper.toResponseDTO(saved));
    }

    @Operation(summary = "Actualizar un material", description = "Permite al ADMINISTRADOR actualizar un material existente por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Material actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Material no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{id}")
    public ResponseEntity<MaterialResponse> update(@PathVariable Integer id, @Valid @RequestBody MaterialRequest materialRequest){
        Material update = service.update(id, mapper.toDomain(materialRequest));
        return ResponseEntity.ok(mapper.toResponseDTO(update));
    }

    @Operation(summary = "Cambiar estado de un material", description = "Permite al ADMINISTRADOR activar o desactivar un material por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Estado actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Material no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Void> cambiarEstado(@PathVariable Integer id, @RequestParam boolean activo){
        service.cambiarEstado(id, activo);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar materiales del almacén del usuario", description = "Permite al ALMACENERO listar los materiales disponibles en su almacén asignado según el token JWT")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de materiales obtenida correctamente"),
            @ApiResponse(responseCode = "401", description = "No autorizado o almacén no asignado")
    })
    @PreAuthorize("hasRole('ALMACENERO')")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/mis-materiales")
    public ResponseEntity<List<MaterialAlmacenResponseDTO>> listarMisMateriales(HttpServletRequest request) {
        Long almacenId = (Long) request.getAttribute("almacenId");
        if (almacenId == null) {
            return ResponseEntity.status(401).build();
        }

        List<DetalleAlmacen> detalles = service.getMaterialesPorAlmacen(almacenId.intValue());
        List<MaterialAlmacenResponseDTO> dtoList = detalles.stream()
                .map(almacenWepMapper::detalleToMaterialAlmacenDTO)
                .toList();
        return ResponseEntity.ok(dtoList);
    }

    @Operation(summary = "Listar materiales de todos los almacenes", description = "Permite al ADMINISTRADOR listar los materiales disponibles de todos los almacenes")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de materiales obtenida correctamente"),
            @ApiResponse(responseCode = "401", description = "No autorizado o almacén no asignado")
    })
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/todos-materiales")
    public ResponseEntity<List<MaterialAlmacenResponseDTO>> listarTodosLosMateriales() {
        // Llamamos al servicio que obtiene todos los detalles de almacén
        List<DetalleAlmacen> detalles = service.findAll();

        // Convertimos cada detalle a DTO
        List<MaterialAlmacenResponseDTO> dtoList = detalles.stream()
                .map(almacenWepMapper::detalleToMaterialAlmacenDTO)
                .toList();

        return ResponseEntity.ok(dtoList);
    }
}
