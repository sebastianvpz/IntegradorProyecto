package com.utp.integradorspringboot.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * Representa un rol que puede tener un usuario dentro del sistema.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @ManyToMany(mappedBy = "roles")
    private Set<Usuario> usuarios;
}
