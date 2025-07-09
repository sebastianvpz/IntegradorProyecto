package com.utp.integradorspringboot.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Restaurante {

    @Id
    private Long id;
    private String nombre;
    private String descripcion;
    private String direccion;
    private String telefono;
    private String correo;
    private String logoUrl;
    private String terminosycondUrl;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getTerminosycondUrl() {
        return terminosycondUrl;
    }

    public void setTerminosycondUrl(String terminosycondUrl) {
        this.terminosycondUrl = terminosycondUrl;
    }
}
