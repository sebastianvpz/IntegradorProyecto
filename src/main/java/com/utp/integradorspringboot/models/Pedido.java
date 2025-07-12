package com.utp.integradorspringboot.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_restaurante", nullable = false)
    private Long restauranteId;

    @Column(name = "id_mesa", nullable = false)
    private Long mesaId;

    @Column(name = "id_usuario", nullable = false)
    private Long usuarioId;

    @Column(name = "hora_inicio")
    private LocalTime horaInicio;

    @Column(name = "hora_entrega")
    private LocalTime horaEntrega;

    @Column(name = "estado_entrega")
    private String estadoEntrega;

    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;

    @Column(name = "subtotal")
    private double subtotal;

    @Column(name = "descuento")
    private double descuento;

    @Column(name = "costo_final")
    private double costoFinal;

    @Column(name = "estado_pago")
    private String estadoPago;

    @Column(name = "comensal")
    private String comensal;

    @Column(name = "n_persona")
    private Integer nPersona;

    @Column(name = "numero_mesa")
    private Integer numeroMesa;
}
