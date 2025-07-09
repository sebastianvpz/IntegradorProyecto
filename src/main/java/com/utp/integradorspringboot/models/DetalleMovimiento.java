package com.utp.integradorspringboot.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "detalles_movimientos")
@Getter
@Setter
public class DetalleMovimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_cierres_de_caja")
    private Long cierreCajaId;

    private String tipo;
    private String descripcion;
    private String monto;
    private LocalDate fecha;
}
