package com.integrador.inventario.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    private Integer idUsuario;
    private Rol rol;
    private String nombre;
    private String correo;
    private String password;
    private boolean estado;
    private String imagenUrl;
    private String publicId;
    private LocalDateTime fechaCreacion;

    public Usuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
}
