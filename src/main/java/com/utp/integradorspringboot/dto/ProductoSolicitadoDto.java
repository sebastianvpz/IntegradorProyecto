package com.utp.integradorspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductoSolicitadoDto {
    private Long id;
    private String nombreProducto;
    private String categoria;
    private Integer cantidad;
    private String fechaSolicitud;
    private String solicitadoPor;
    private String estado;
    private Long productoId;

}
