package com.utp.integradorspringboot.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "productos")
@Getter
@Setter
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_restaurante", nullable = false)
    private Long idRestaurante;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private String categoria;

    @Column(name = "fecha_vencimiento")
    private String fechaVencimiento;

    @Column(name = "cantidad_minima")
    private Integer cantidadMinima = 0;

    @Column(name = "fecha_ingreso")
    private String fechaIngreso;

    @Column
    private String estado = "activo";
}
