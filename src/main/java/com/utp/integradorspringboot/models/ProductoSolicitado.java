package com.utp.integradorspringboot.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "productos_solicitados")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoSolicitado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id")
    private Producto producto;

    private Integer cantidad;

    @Column(name = "fecha_solicitud")
    private LocalDateTime fechaSolicitud;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitado_por")
    private Usuario solicitadoPor;

    @Column(name = "estado")
    private String estado;

    @Column(name = "restaurante_id", nullable = false)
    private Long restauranteId;

}
