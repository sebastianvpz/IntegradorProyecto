package com.utp.integradorspringboot.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_pedido", nullable = false)
    private Long pedidoId;

    @Column(name = "monto", nullable = false)
    private Double monto;

    @Column(name = "medio_pago", nullable = false)
    private String medioPago;

    @Column(name = "observacion")
    private String observacion;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDateTime fechaPago;

    @Column(name = "id_usuario", nullable = false)
    private Long usuarioId;
}
