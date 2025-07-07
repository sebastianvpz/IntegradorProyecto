package com.utp.integradorspringboot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "mesas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_restaurante", nullable = false)
    private Long restauranteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_piso", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Zona zona;

    @Column(name = "numero_mesa", nullable = false)
    private String numeroMesa;

    @Column(nullable = false)
    private Integer capacidad;

    @Column
    private String etiqueta;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column
    private String estado = "disponible";

    @Column(name = "pos_x")
    private Integer posX;

    @Column(name = "pos_y")
    private Integer posY;

}
