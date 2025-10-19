package com.integrador.inventario.percistence.tipoMaterial.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tipo_material")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TipoMaterialEntity {

    @Id
    @Column(name = "id_tipo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "nombre_tipo", nullable = false, length = 50, unique = true)
    String name;
}
