package com.utp.integradorspringboot.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuración global de seguridad utilizando Spring Security y JWT.
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http /*, JwtAuthenticationFilter jwtFilter */) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // ← Permitir TODO temporalmente (sin autenticación)
            )
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // ← Comentar temporalmente el filtro JWT
        // .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
    // Configuración original (comentar mientras se desactiva seguridad)

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           JwtAuthenticationFilter jwtFilter) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers(HttpMethod.POST, "/comensal/reservar").permitAll()
                    .requestMatchers(
                        "/api/auth/**", "/login", "/usuarios", "/favicon.ico", 
                        "/inventario", "/productos-solicitados", "/pisos", 
                        "/platos", "/dashboard", "/cambiar-contrasenia", 
                        "/perfil", "/comensal", "/comensal/**", 
                        "/comensal/reservar", "/comensal/reservar/**"
                    ).permitAll()
                    .requestMatchers(HttpMethod.GET, "/comensal/**").permitAll()
                    .requestMatchers("/admin/reservas", "/admin/reservas/**").hasRole("ADMIN")
                    .requestMatchers("/api/reservas/**").hasRole("ADMIN")
                    .requestMatchers("/api/productos/**").hasAnyRole("ADMIN", "CHEF")
                    .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    */
}
