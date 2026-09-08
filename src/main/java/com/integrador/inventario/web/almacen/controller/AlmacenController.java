package com.integrador.inventario.web.almacen.controller;

import com.integrador.inventario.domain.model.Almacen;
import com.integrador.inventario.domain.service.almacen.AlmacenService;
import com.integrador.inventario.web.almacen.dto.AlmacenRequestDTO;
import com.integrador.inventario.web.almacen.dto.AlmacenResponseDTO;
import com.integrador.inventario.web.almacen.dto.MaterialesAgotadosDTO;
import com.integrador.inventario.web.almacen.dto.StockTotalResponseDTO;
import com.integrador.inventario.web.almacen.mapper.AlmacenWepMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/almacen")
public class AlmacenController {

    private final AlmacenService almacenService;
    private final AlmacenWepMapper mapper;

    public AlmacenController(AlmacenService almacenService, AlmacenWepMapper mapper) {
        this.almacenService = almacenService;
        this.mapper = mapper;
    }

    @Operation(
            summary = "Obtener todos los almacenes",
            description = "Devuelve la lista completa de almacenes registrados. Requiere rol ADMINISTRADOR."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de almacenes devuelta correctamente"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public ResponseEntity<List<AlmacenResponseDTO>> getAll() {
        List<AlmacenResponseDTO> lista = almacenService.getAll()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(lista);
    }

    @Operation(
            summary = "Obtener almacén por ID",
            description = "Devuelve un almacén específico según su ID. Requiere rol ADMINISTRADOR."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Almacén encontrado"),
            @ApiResponse(responseCode = "404", description = "Almacén no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{id}")
    public ResponseEntity<AlmacenResponseDTO> getById(@PathVariable Integer id) {
        return almacenService.getById(id)
                .map(mapper::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Crear un nuevo almacén",
            description = "Permite registrar un nuevo almacén. Requiere rol ADMINISTRADOR."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Almacén creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<AlmacenResponseDTO> save(@Valid @RequestBody AlmacenRequestDTO dto) {
        Almacen almacen = mapper.toDomain(dto);
        Almacen guardado = almacenService.save(almacen);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDTO(guardado));
    }

    @Operation(
            summary = "Actualizar un almacén",
            description = "Permite actualizar los datos de un almacén existente. Requiere rol ADMINISTRADOR."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Almacén actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Almacén no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{id}")
    public ResponseEntity<AlmacenResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody AlmacenRequestDTO dto) {
        Almacen almacen = mapper.toDomain(dto);
        Almacen actualizado = almacenService.update(id, almacen);
        return ResponseEntity.ok(mapper.toResponseDTO(actualizado));
    }

    @Operation(
            summary = "Cambiar estado del almacén",
            description = "Permite activar o desactivar un almacén existente. Requiere rol ADMINISTRADOR."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Estado actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Almacén no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Void> cambiarEstado(
            @PathVariable Integer id,
            @RequestParam boolean activo
    ) {
        almacenService.updateEstado(id, activo);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Obtener stock total",
            description = "Devuelve la suma total de los stocks de todos los almacenes. Requiere rol ADMINISTRADOR."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Stock total devuelto correctamente"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/stock-total")
    public ResponseEntity<StockTotalResponseDTO> obtenerStockTotal() {
        Integer stockTotal = almacenService.obtenerStockTotal();
        return ResponseEntity.ok(new StockTotalResponseDTO(stockTotal));
    }

    @Operation(
            summary = "Obtener KPI de materiales agotados",
            description = "Devuelve la cantidad de materiales que están agotados en al menos un almacén. Requiere rol ADMINISTRADOR."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "KPI devuelto correctamente"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/kpi/materiales-agotados")
    public ResponseEntity<MaterialesAgotadosDTO> obtenerMaterialesAgotados() {
        Integer cantidad = almacenService.contarMaterialesAgotados();
        return ResponseEntity.ok(new MaterialesAgotadosDTO(cantidad));
    }

}
