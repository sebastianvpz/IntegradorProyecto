package com.utp.integradorspringboot.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "pedidos")
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_restaurante", nullable = false)
    private Long idRestaurante;

    @Column(name = "id_mesa", nullable = false)
    private Long idMesa;

    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

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

    @Column(name = "numero_mesa")
    private Integer numeroMesa;

    @Column(name = "n_persona")
    private Integer nPersona;

    public Pedido() {
    }

    public Pedido(Long id, Long idRestaurante, Long idMesa, Long idUsuario, LocalTime horaInicio, LocalTime horaEntrega, String estadoEntrega, LocalDate fechaCreacion, double subtotal, double descuento, double costoFinal, String estadoPago, String comensal, Integer numeroMesa, Integer nPersona) {
        this.id = id;
        this.idRestaurante = idRestaurante;
        this.idMesa = idMesa;
        this.idUsuario = idUsuario;
        this.horaInicio = horaInicio;
        this.horaEntrega = horaEntrega;
        this.estadoEntrega = estadoEntrega;
        this.fechaCreacion = fechaCreacion;
        this.subtotal = subtotal;
        this.descuento = descuento;
        this.costoFinal = costoFinal;
        this.estadoPago = estadoPago;
        this.comensal = comensal;
        this.numeroMesa = numeroMesa;
        this.nPersona = nPersona;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(Long idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public Long getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(Long idMesa) {
        this.idMesa = idMesa;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraEntrega() {
        return horaEntrega;
    }

    public void setHoraEntrega(LocalTime horaEntrega) {
        this.horaEntrega = horaEntrega;
    }

    public String getEstadoEntrega() {
        return estadoEntrega;
    }

    public void setEstadoEntrega(String estadoEntrega) {
        this.estadoEntrega = estadoEntrega;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getCostoFinal() {
        return costoFinal;
    }

    public void setCostoFinal(double costoFinal) {
        this.costoFinal = costoFinal;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    public String getComensal() {
        return comensal;
    }

    public void setComensal(String comensal) {
        this.comensal = comensal;
    }

    public Integer getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(Integer numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public Integer getnPersona() {
        return nPersona;
    }

    public void setnPersona(Integer nPersona) {
        this.nPersona = nPersona;
    }

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Pedido)) {
            return false;
        }
        Pedido other = (Pedido) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }
}