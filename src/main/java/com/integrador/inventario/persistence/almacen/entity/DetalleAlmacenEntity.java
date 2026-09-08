package com.integrador.inventario.persistence.almacen.entity;

import com.integrador.inventario.persistence.material.entity.MaterialEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "almacen_material")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleAlmacenEntity {

    @EmbeddedId
    private DetalleAlmacenId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idAlmacen")
    @JoinColumn(name = "id_almacen", nullable = false)
    private AlmacenEntity almacen;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idMaterial")
    @JoinColumn(name = "id_material", nullable = false)
    private MaterialEntity material;

    @Column(name = "stock_actual", nullable = false)
    private Integer stockActual;

    // Clase embebida para clave primaria compuesta
    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DetalleAlmacenId implements java.io.Serializable {
        private Integer idAlmacen;
        private Integer idMaterial;
    }
}
