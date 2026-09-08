package com.integrador.inventario.domain.model;

import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Almacen {

    private Integer idAlmacen;
    private Usuario usuario;
    private String codigoAlmacen;
    private String nombre;
    private String direccion;
    private String ciudad;
    private String provincia;
    private String region;
    private Integer capacidadMaxima;
    private String telefonoContacto;
    private Boolean estado;
    private LocalDateTime fechaCreacion;

    private List<DetalleAlmacen> materiales = new ArrayList<>();

    // Inicializar valores por defecto si no se asignan
    public void initDefaults() {
        if (estado == null) {
            estado = true;
        }
        if (fechaCreacion == null) {
            fechaCreacion = LocalDateTime.now();
        }
    }

    public Almacen(Integer idAlmacen) {
        this.idAlmacen = idAlmacen;
    }
}
