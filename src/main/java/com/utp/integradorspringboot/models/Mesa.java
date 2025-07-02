/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.integradorspringboot.models;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 *
 * @author jcerv
 */
@Entity
@Table(name = "mesa")
public class Mesa implements Serializable{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "id_restaurante", nullable = false)
    private Long idRestaurante;

    @Column(name = "id_piso", nullable = false)
    private Long idPiso;

    @Column(name = "numero_mesa")
    private Long numeroMesa;
    
    @Column(name = "capacidad")
    private Long capacidad;
    
    @Column(name = "etiqueta")
    private String etiqueta;
    
    @Column(name = "fecha_creacion")
    private String fechaCreacion;
    
    @Column(name = "estado")
    private String estado;

    public Mesa() {
    }

    public Mesa(Long id, Long idRestaurante, Long idPiso, Long numeroMesa, Long capacidad, String etiqueta, String fechaCreacion, String estado) {
        this.id = id;
        this.idRestaurante = idRestaurante;
        this.idPiso = idPiso;
        this.numeroMesa = numeroMesa;
        this.capacidad = capacidad;
        this.etiqueta = etiqueta;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
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

    public Long getIdPiso() {
        return idPiso;
    }

    public void setIdPiso(Long idPiso) {
        this.idPiso = idPiso;
    }

    public Long getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(Long numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public Long getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Long capacidad) {
        this.capacidad = capacidad;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Mesa)) {
            return false;
        }
        Mesa other = (Mesa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
}