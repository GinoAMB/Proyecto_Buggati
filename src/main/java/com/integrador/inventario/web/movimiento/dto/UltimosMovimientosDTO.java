package com.integrador.inventario.web.movimiento.dto;

import com.integrador.inventario.persistence.movimiento.enums.TipoMovimiento;

import java.time.LocalDateTime;

public record UltimosMovimientosDTO(
        TipoMovimiento tipoMovimiento,
        Integer cantidad,
        LocalDateTime fechaHora
) { }
