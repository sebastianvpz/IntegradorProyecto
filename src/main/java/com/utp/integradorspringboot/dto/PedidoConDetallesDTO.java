package com.utp.integradorspringboot.dto;

import java.util.List;

public class PedidoConDetallesDTO {
    // Campos de Pedido
    private Long idRestaurante;
    private Long idUsuario;
    private Integer numeroMesa;
    private String comensal;
    private Integer nPersona;
    private Double subtotal;
    private Double descuento;
    private Double costoFinal;
    private String estadoEntrega;
    private String estadoPago;

    private List<DetallePedidoDTO> platos;

    public Long getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(Long idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(Integer numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public String getComensal() {
        return comensal;
    }

    public void setComensal(String comensal) {
        this.comensal = comensal;
    }

    public Integer getnPersona() {
        return nPersona;
    }

    public void setnPersona(Integer nPersona) {
        this.nPersona = nPersona;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public Double getCostoFinal() {
        return costoFinal;
    }

    public void setCostoFinal(Double costoFinal) {
        this.costoFinal = costoFinal;
    }

    public String getEstadoEntrega() {
        return estadoEntrega;
    }

    public void setEstadoEntrega(String estadoEntrega) {
        this.estadoEntrega = estadoEntrega;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    public List<DetallePedidoDTO> getPlatos() {
        return platos;
    }

    public void setPlatos(List<DetallePedidoDTO> platos) {
        this.platos = platos;
    }

    
}
