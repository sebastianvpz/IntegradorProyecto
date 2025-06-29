package com.utp.integradorspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioDto {
    private Long id;
    private String nombre;
    private String correo;
    private String contrasenia;

    private LocalDateTime fechaCreacion;
    private String rol;
}
