package com.utp.integradorspringboot.dto;
import java.time.LocalDateTime;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {
    private Long id;
    private String nombre;
    private String correo;
    private String contrasenia;
    private String estado;
    private Long restauranteId;
    private LocalDateTime fechaCreacion;
    private Integer rolId;
}

