package com.utp.integradorspringboot.models;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "cierres_de_caja")
public class CierreCaja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_restaurante")
    private Integer idRestaurante;

    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_cierre")
    private LocalDate fechaCierre;

    @Column(name = "total_ventas")
    private BigDecimal totalVentas;

    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;

    @Column(name = "total_efectivo")
    private BigDecimal totalEfectivo;

    @Column(name = "total_tarjeta_credito")
    private BigDecimal totalTarjetaCredito;

    @Column(name = "total_tarjeta_debito")
    private BigDecimal totalTarjetaDebito;

    @Column(name = "total_egresos")
    private BigDecimal totalEgresos;

    @Column(name = "saldo_final")
    private BigDecimal saldoFinal;

    private String estado;

    // ======= GETTERS & SETTERS =======
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getIdRestaurante() { return idRestaurante; }
    public void setIdRestaurante(Integer idRestaurante) { this.idRestaurante = idRestaurante; }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaCierre() { return fechaCierre; }
    public void setFechaCierre(LocalDate fechaCierre) { this.fechaCierre = fechaCierre; }

    public BigDecimal getTotalVentas() { return totalVentas; }
    public void setTotalVentas(BigDecimal totalVentas) { this.totalVentas = totalVentas; }

    public LocalDate getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDate fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public BigDecimal getTotalEfectivo() { return totalEfectivo; }
    public void setTotalEfectivo(BigDecimal totalEfectivo) { this.totalEfectivo = totalEfectivo; }

    public BigDecimal getTotalTarjetaCredito() { return totalTarjetaCredito; }
    public void setTotalTarjetaCredito(BigDecimal totalTarjetaCredito) { this.totalTarjetaCredito = totalTarjetaCredito; }

    public BigDecimal getTotalTarjetaDebito() { return totalTarjetaDebito; }
    public void setTotalTarjetaDebito(BigDecimal totalTarjetaDebito) { this.totalTarjetaDebito = totalTarjetaDebito; }

    public BigDecimal getTotalEgresos() { return totalEgresos; }
    public void setTotalEgresos(BigDecimal totalEgresos) { this.totalEgresos = totalEgresos; }

    public BigDecimal getSaldoFinal() { return saldoFinal; }
    public void setSaldoFinal(BigDecimal saldoFinal) { this.saldoFinal = saldoFinal; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
