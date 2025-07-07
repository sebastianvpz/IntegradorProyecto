package com.utp.integradorspringboot.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "zonas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Zona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_restaurante", nullable = false)
    private Long restauranteId;

    @Column(nullable = false)
    private String nombre;

    @Column(name = "cantidad_mesas")
    private Integer cantidadMesas;

    @Column
    private String nota;

    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion = LocalDate.now();

    @Column
    private String estado = "activo";
}
