package com.utp.integradorspringboot.dto;

/**
 * DTO que representa la respuesta de autenticación (login) con el token generado.
 */
public class AuthResponse {
    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
