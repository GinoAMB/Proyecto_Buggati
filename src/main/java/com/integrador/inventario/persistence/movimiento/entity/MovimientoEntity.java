package com.integrador.inventario.persistence.movimiento.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.integrador.inventario.persistence.almacen.entity.AlmacenEntity;
import com.integrador.inventario.persistence.movimiento.enums.TipoMovimiento;
import com.integrador.inventario.persistence.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "movimiento")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimiento")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_almacen", nullable = false)
    private AlmacenEntity almacen;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private UserEntity usuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimiento", nullable = false)
    private TipoMovimiento tipoMovimiento;

    @CreationTimestamp
    @Column(name = "fecha_hora", updatable = false)
    private LocalDateTime fechaHora;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "referencia", length = 100, nullable = true)
    private String referencia;

    // 🔹 Relación con los detalles del movimiento
    @OneToMany(mappedBy = "movimiento", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<DetalleMovimientoEntity> detalles;
}

