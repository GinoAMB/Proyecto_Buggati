package com.integrador.inventario.persistence.material.entity;

import com.integrador.inventario.persistence.tipoMaterial.entity.TipoMaterialEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "material")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaterialEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_material")
    private Integer id;

    // Relación con tipo_material
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo", nullable = false)
    private TipoMaterialEntity tipoMaterial;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "stock_minimo")
    private Integer stockMin;

    @Column(name = "imagen_url", length = 255)
    private String imagenUrl;

    @Column(name = "public_id", length = 255)
    private String publicId;

    @Column(name = "estado", nullable = false)
    private Boolean estado = true;

    @Column(name = "unidad_medida", length = 50)
    private String unidadMedida;
}
