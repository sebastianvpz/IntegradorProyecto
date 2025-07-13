package com.utp.integradorspringboot.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reservas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombres_comensal", nullable = false)
    private String nombresComensal;

    @Column(name = "apellidos_comensal", nullable = false)
    private String apellidosComensal;

    @Column(name = "correo_comensal", nullable = false)
    private String correoComensal;

    @Column(name = "telefono_comensal", nullable = false)
    private Long telefonoComensal;

    @Column(name = "ocasion")
    private String ocasion;

    @Column(name = "fecha_reserva", nullable = false)
    private LocalDate fechaReserva;

    @Column(name = "hora_reserva", nullable = false)
    private LocalTime horaReserva;

    @Column(name = "numero_personas", nullable = false)
    private Integer numeroPersonas;

    @Column(name = "id_mesa", nullable = false)
    private Integer idMesa;

    @Column(name = "fecha_creacion")
    private String fechaCreacion;

    @Column(name = "id_restaurante", nullable = false)
    private Integer idRestaurante;

    @Column(name = "estado", nullable = false)
    private String estado = "reservado";
}
