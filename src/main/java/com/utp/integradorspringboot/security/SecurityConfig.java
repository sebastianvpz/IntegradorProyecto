package com.utp.integradorspringboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuración global de seguridad utilizando Spring Security y JWT.
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           JwtAuthenticationFilter jwtFilter) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**","/login","/usuarios", "/favicon.ico", "/inventario",
                                "/productos-solicitados","/pisos","/platos","/dashboard","/cambiar-contrasenia",
                                "/perfil","/nueva-password","/recuperar","/nuevospedidos","/pedidos","/mesas","/reservas","/comensal/{idRestaurante}",
                                "/comensal/reservar","/api/platos/public/**","/inventariochef","/caja","/reservas-mozo/**").permitAll()
                        .requestMatchers("/api/dashboard/**","/api/caja/**").hasAnyRole("ADMIN")
                        .requestMatchers("/api/usuarios/**").hasAnyRole("ADMIN", "CHEF","MOZO")
                        .requestMatchers("/api/productos/**","/api/solicitados/**").hasAnyRole("ADMIN", "CHEF")
                        .requestMatchers("/api/platos/**","/api/mesas/**","/api/zonas/**","/api/reservas/**").hasAnyRole("MOZO","ADMIN")
                        .requestMatchers("/api/pagos/**", "/api/pedidos/**" ,"/api/detalles_pedido/**").hasAnyRole("MOZO")
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

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
}
