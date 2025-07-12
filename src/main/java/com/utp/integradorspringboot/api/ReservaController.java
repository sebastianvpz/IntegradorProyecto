package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.models.Reserva;
import com.utp.integradorspringboot.repositories.ReservaRepository;
import com.utp.integradorspringboot.security.JwtUtil;
import com.utp.integradorspringboot.services.ReservaService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * API REST para la gestión de reservas.
 */
@RestController
@RequestMapping("/api/reservas")
@CrossOrigin
public class ReservaController {

   @Autowired
private ReservaService reservaService;

@GetMapping
public List<Reserva> listarReservas(@RequestParam(required = false) LocalDate fecha, HttpSession session) {
    Integer restauranteId = (Integer) session.getAttribute("restauranteId");
    return (fecha != null)
        ? reservaService.obtenerPorRestauranteYFecha(restauranteId, fecha)
        : reservaService.obtenerPorRestaurante(restauranteId);
}
}
