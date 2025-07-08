package com.utp.integradorspringboot.dto;
import java.time.LocalDateTime;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String correo;
    private String contrasenia;
    private String estado;
    private Long restauranteId;
    private LocalDateTime fechaCreacion;
    private Integer rolId; // Integer si tu Rol usa Integer como ID
    // Getters y Setters
}

