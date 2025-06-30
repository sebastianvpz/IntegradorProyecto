package com.utp.integradorspringboot.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reservas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "telefono_comensal", nullable = false)
    private Long telefonoComensal;

    @Column(name = "fecha_reserva", nullable = false)
    private String fechaReserva;  // formato "yyyy-MM-dd"

    @Column(name = "hora_reserva", nullable = false)
    private String horaReserva;   // formato "HH:mm"

    @Column(name = "numero_personas", nullable = false)
    private Integer numeroPersonas;

    @Column(name = "id_mesa", nullable = false)
    private Integer idMesa;

    @Column(name = "id_restaurante", nullable = false)
    private Integer idRestaurante;

    @Column(name = "nombres_comensal", length = 255, nullable = false)
    private String nombresComensal;

    @Column(name = "apellidos_comensal", length = 255, nullable = false)
    private String apellidosComensal;

    @Column(name = "correo_comensal", length = 255, nullable = false)
    private String correoComensal;

    @Column(name = "estado", length = 100)
    private String estado = "reservado";

    @Column(name = "ocasion", length = 255)
    private String ocasion;

    @Column(name = "fecha_creacion")
    private String fechaCreacion;
}
