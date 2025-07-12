package com.utp.integradorspringboot.models;

import jakarta.persistence.*;  // ✅ Usar Jakarta para Spring Boot 3
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "restaurantes")
@Getter
@Setter
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "correo")
    private String correo;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "terminosycond_url")
    private String terminosycondUrl;

    // Constructor vacío necesario para JPA
    public Restaurante() {}
}
