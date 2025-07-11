package com.utp.integradorspringboot.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
public class Pago implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_pedido", nullable = false)
    private Long idPedido;

    @Column(name = "monto", nullable = false)
    private Double monto;

    @Column(name = "medio_pago", length = 100)
    private String medioPago;

    @Column(name = "observacion")
    private String observacion;

    @Column(name = "fecha_pago")
    private LocalDateTime fechaPago;

    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    public Pago() {
    }

    public Pago(Long id, Long idPedido, Double monto, String medioPago, String observacion, LocalDateTime fechaPago, Long idUsuario) {
        this.id = id;
        this.idPedido = idPedido;
        this.monto = monto;
        this.medioPago = medioPago;
        this.observacion = observacion;
        this.fechaPago = fechaPago;
        this.idUsuario = idUsuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        return (id != null ? id.hashCode() : 0);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Pago)) return false;
        Pago other = (Pago) obj;
        return (this.id != null && this.id.equals(other.id));
    }
}
