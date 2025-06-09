package com.utp.integradorspringboot.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "reservas")
@Getter
@Setter
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombres_comensal", nullable = false)
    private String nombresComensal;

    @Column(name = "apellidos_comensal", nullable = false)
    private String apellidosComensal;

    @Column(name = "correo_comensal", nullable = false)
    private String correoComensal;

    @Column(name = "telefono_comensal", nullable = false)
    private Long telefonoComensal;

    @Column
    private String ocasion;

    @Column(name = "fecha_reserva", nullable = false)
    private String fechaReserva;

    @Column(name = "hora_reserva", nullable = false)
    private String horaReserva;

    @Column(name = "numero_personas", nullable = false)
    private Integer numeroPersonas;

    @Column(name = "id_mesa", nullable = false)
    private Integer idMesa;

    @Column(name = "fecha_creacion")
    private String fechaCreacion;

    @Column(name = "id_restaurante", nullable = false)
    private Integer idRestaurante;

    @Column
    private String estado = "reservado";
}
