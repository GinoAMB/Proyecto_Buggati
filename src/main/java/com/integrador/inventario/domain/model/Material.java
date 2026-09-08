package com.integrador.inventario.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Material {

    private Integer id;
    private TipoMaterial tipoMaterial;
    private String nombre;
    private String descripcion;
    private Integer stockMin;
    private String imagenUrl;
    private String publicId;
    private Boolean estado;
    private String unidadMedida;

    public Material(Integer id) {
        this.id = id;
    }
}
