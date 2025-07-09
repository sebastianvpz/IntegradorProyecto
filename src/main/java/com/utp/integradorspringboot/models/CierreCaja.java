package com.utp.integradorspringboot.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "cierres_de_caja")
@Getter
@Setter
public class CierreCaja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_restaurante")
    private Long restauranteId;

    @Column(name = "id_usuario")
    private Long usuarioId;

    private LocalDate fechaInicio;
    private LocalDate fechaCierre;
    private LocalDate fechaCreacion;

    private BigDecimal totalVentas;
    private BigDecimal totalEfectivo;
    private BigDecimal totalTarjetaCredito;
    private BigDecimal totalTarjetaDebito;
    private BigDecimal totalEgresos;
    private BigDecimal saldoFinal;

    private String estado;

   

}
