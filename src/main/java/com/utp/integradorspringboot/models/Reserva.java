package com.utp.integradorspringboot.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reservas")
@Getter
@Setter
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombres_comensal")
    private String nombresComensal;

    @Column(name = "apellidos_comensal")
    private String apellidosComensal;

    @Column(name = "correo_comensal")
    private String correoComensal;

    @Column(name = "telefono_comensal")
    private Long telefonoComensal;

    @Column(name = "ocasion")
    private String ocasion;

    @Column(name = "fecha_reserva")
    private LocalDate fechaReserva;

    @Column(name = "hora_reserva")
    private LocalTime horaReserva;

    @Column(name = "numero_personas")
    private Integer numeroPersonas;

    @Column(name = "id_mesa")
    private Integer idMesa;

    @Column(name = "estado")
    private String estado = "reservado";

    @Column(name = "id_restaurante")
    private Integer idRestaurante;

    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;

    public Reserva() {}

    public Reserva(String nombresComensal, String apellidosComensal, String correoComensal, Long telefonoComensal,
                   String ocasion, LocalDate fechaReserva, LocalTime horaReserva, Integer numeroPersonas,
                   Integer idMesa, Integer idRestaurante) {
        this.nombresComensal = nombresComensal;
        this.apellidosComensal = apellidosComensal;
        this.correoComensal = correoComensal;
        this.telefonoComensal = telefonoComensal;
        this.ocasion = ocasion;
        this.fechaReserva = fechaReserva;
        this.horaReserva = horaReserva;
        this.numeroPersonas = numeroPersonas;
        this.idMesa = idMesa;
        this.idRestaurante = idRestaurante;
        this.estado = "reservado";
        this.fechaCreacion = LocalDate.now();
    }

    // ✅ Ya no hay UnsupportedOperationException
    public void setEstado(String estado) {
        this.estado = estado;
    }
}
