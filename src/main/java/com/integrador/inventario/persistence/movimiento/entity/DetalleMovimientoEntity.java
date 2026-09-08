package com.integrador.inventario.persistence.movimiento.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.integrador.inventario.persistence.material.entity.MaterialEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "movimiento_detalle")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalleMovimientoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_movimiento", nullable = false)
    @JsonBackReference
    private MovimientoEntity movimiento;

    @ManyToOne
    @JoinColumn(name = "id_material", nullable = false)
    private MaterialEntity material;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;
}
