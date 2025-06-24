package com.utp.integradorspringboot.security;



import com.utp.integradorspringboot.models.Usuario;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Utilitario para la generación y validación de tokens JWT.
 */
@Component
public class JwtUtil {

    private static final String SECRET_KEY = "18ac29eeac37421d6f36dc2ed31976382220f89fbf0706d1497c8362c500517e";
    private SecretKey key;
    private JwtParser parser;

    /**
     * Inicializa la clave secreta y el parser para JWT.
     */
    @PostConstruct
    public void init() {
        System.out.println(">>> Inicializando JwtUtil...");
        if (SECRET_KEY.length() < 32) {
            throw new IllegalArgumentException("La clave SECRET_KEY es muy corta: longitud = " + SECRET_KEY.length());
        }
        this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        this.parser = Jwts.parser().verifyWith(key).build();
    }

    /**
     * Genera un token JWT válido por 5 horas.
     * @param usuario el usuario autenticado
     * @return token JWT
     */
    public String generarToken(Usuario usuario) {
        long tiempoExpiracion = 1000 * 60 * 60 * 5;

        String roles = usuario.getRoles().stream()
                .map(r -> r.getNombre())
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .subject(usuario.getCorreo())
                .claim("id", usuario.getId())
                .claim("restauranteId", usuario.getRestauranteId())
                .claim("roles", roles)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + tiempoExpiracion))
                .signWith(key)
                .compact();
    }

    /**
     * Extrae los claims del token.
     * @param token JWT
     * @return los claims
     */
    public Claims extraerClaims(String token) {
        return parser.parseSignedClaims(token).getPayload();
    }

    /**
     * Obtiene el correo desde el token.
     * @param token JWT
     * @return correo
     */
    public String obtenerCorreo(String token) {
        return extraerClaims(token).getSubject();
    }

    /**
     * Verifica si un token es válido.
     * @param token JWT
     * @return true si válido, false si inválido
     */
    public boolean esValido(String token) {
        try {
            extraerClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}