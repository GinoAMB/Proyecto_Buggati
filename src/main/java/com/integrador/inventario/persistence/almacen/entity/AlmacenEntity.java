package com.integrador.inventario.persistence.almacen.entity;


import com.integrador.inventario.persistence.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "almacen")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlmacenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_almacen")
    private Integer idAlmacen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private UserEntity usuario;

    @Column(name = "codigo_almacen", unique = true, length = 50)
    private String codigoAlmacen;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "direccion", length = 150)
    private String direccion;

    @Column(name = "ciudad", length = 100)
    private String ciudad;

    @Column(name = "provincia", length = 100)
    private String provincia;

    @Column(name = "region", length = 100)
    private String region;

    @Column(name = "capacidad_maxima")
    private Integer capacidadMaxima;

    @Column(name = "telefono_contacto", length = 20)
    private String telefonoContacto;

    @Column(name = "estado")
    private Boolean estado = true;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();

}
