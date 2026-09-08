package com.integrador.inventario.web.movimiento.controller;

import com.integrador.inventario.domain.model.Movimiento;
import com.integrador.inventario.domain.service.movimiento.MovimientoService;
import com.integrador.inventario.persistence.movimiento.enums.TipoMovimiento;
import com.integrador.inventario.web.movimiento.dto.MovimientoEntradaDTO;
import com.integrador.inventario.web.movimiento.dto.MovimientoResponseDTO;
import com.integrador.inventario.web.movimiento.dto.MovimientosHoyResponseDTO;
import com.integrador.inventario.web.movimiento.dto.UltimosMovimientosDTO;
import com.integrador.inventario.web.movimiento.mapper.MovimientoWebMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/movimiento")
public class MovimientoController {

    private final MovimientoService service;
    private final MovimientoWebMapper mapper;

    public MovimientoController(MovimientoService service, MovimientoWebMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    // ============================================================
    //                      REGISTRAR ENTRADA
    // ============================================================
    @Operation(summary = "Registrar entrada de material", description = "Permite al ALMACENERO registrar la entrada de materiales en su almacén")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Entrada registrada correctamente"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    @PreAuthorize("hasRole('ALMACENERO')")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/entrada")
    public ResponseEntity<MovimientoResponseDTO> registrarEntrada(
            @Valid @RequestBody MovimientoEntradaDTO dto,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("userId");
        Long almacenId = (Long) request.getAttribute("almacenId");

        Movimiento movimiento = mapper.toDomainEntrada(dto, almacenId.intValue(), userId.intValue());
        Movimiento movGuardado = service.registrarEntrada(movimiento);
        return ResponseEntity.ok(mapper.toResponse(movGuardado));
    }

    // ============================================================
    //                      REGISTRAR SALIDA
    // ============================================================
    @Operation(summary = "Registrar salida de material", description = "Permite al ALMACENERO registrar la salida de materiales de su almacén")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Salida registrada correctamente"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    @PreAuthorize("hasRole('ALMACENERO')")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/salida")
    public ResponseEntity<MovimientoResponseDTO> registrarSalida(
            @Valid @RequestBody MovimientoEntradaDTO dto,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("userId");
        Long almacenId = (Long) request.getAttribute("almacenId");

        Movimiento movimiento = mapper.toDomainSalida(dto, almacenId.intValue(), userId.intValue());
        Movimiento movGuardado = service.registrarSalida(movimiento);
        return ResponseEntity.ok(mapper.toResponse(movGuardado));
    }

    // ============================================================
    //                     OBTENER POR ID
    // ============================================================
    @Operation(summary = "Obtener movimiento por ID", description = "Permite al ADMINISTRADOR obtener la información de un movimiento específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Movimiento encontrado"),
            @ApiResponse(responseCode = "404", description = "Movimiento no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{id}")
    public ResponseEntity<MovimientoResponseDTO> obtener(@PathVariable Integer id) {
        return service.obtenerPorId(id)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ============================================================
    //                     LISTAR TODOS
    // ============================================================
    @Operation(summary = "Listar todos los movimientos", description = "Permite al ADMINISTRADOR obtener una lista de todos los movimientos registrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de movimientos obtenida correctamente"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public ResponseEntity<List<MovimientoResponseDTO>> listar() {
        List<MovimientoResponseDTO> lista = service.listarTodos()
                .stream()
                .map(mapper::toResponse)
                .toList();

        return ResponseEntity.ok(lista);
    }

    // ============================================================
    //           CANTIDAD DE MOVIMIENTOS HOY
    // ============================================================
    @Operation(summary = "Contar movimientos del día", description = "Permite al ADMINISTRADOR obtener la cantidad de movimientos registrados hoy")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cantidad de movimientos obtenida correctamente"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/hoy/cantidad")
    public ResponseEntity<MovimientosHoyResponseDTO> contarMovimientosHoy() {
        long cantidad = service.contarMovimientosHoy();
        String fecha = LocalDate.now().toString();
        MovimientosHoyResponseDTO response = new MovimientosHoyResponseDTO(fecha, cantidad);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ultimos")
    @Operation(summary = "Obtener últimos 5 movimientos", description = "Devuelve los últimos 5 movimientos registrados con tipo, cantidad total y fecha-hora")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<List<UltimosMovimientosDTO>> ultimosMovimientos() {

        List<Movimiento> ultimos = service.obtenerUltimosMovimientos(5);

        List<UltimosMovimientosDTO> dtoList = ultimos.stream()
                .map(m -> new UltimosMovimientosDTO(
                        m.getTipoMovimiento().equals("ENTRADA") ? TipoMovimiento.ENTRADA : TipoMovimiento.SALIDA,
                        m.getDetalles().stream().mapToInt(d -> d.getCantidad()).sum(),
                        m.getFechaHora()
                ))
                .toList();

        return ResponseEntity.ok(dtoList);
    }

}
