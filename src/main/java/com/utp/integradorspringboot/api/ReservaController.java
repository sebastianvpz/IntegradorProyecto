package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.models.Reserva;
import com.utp.integradorspringboot.security.JwtUtil;
import com.utp.integradorspringboot.services.ReservaService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping
    public ResponseEntity<List<Reserva>> listarPorRestaurante(HttpServletRequest request) {
        String token = jwtUtil.obtenerTokenDesdeRequest(request);
        Integer restauranteId = jwtUtil.extraerRestauranteId(token).intValue();

        List<Reserva> reservas = reservaService.obtenerPorRestaurante(restauranteId);
        return ResponseEntity.ok(reservas);
    }

    @PutMapping("/{id}/confirmar")
    public ResponseEntity<String> confirmar(@PathVariable Integer id, HttpServletRequest request) {
        return actualizarEstado(id, "realizada", request);
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<String> cancelar(@PathVariable Integer id, HttpServletRequest request) {
        return actualizarEstado(id, "cancelada", request);
    }

    private ResponseEntity<String> actualizarEstado(Integer id, String nuevoEstado, HttpServletRequest request) {
        String token = jwtUtil.obtenerTokenDesdeRequest(request);
        Integer restauranteId = jwtUtil.extraerRestauranteId(token).intValue();

        return reservaService.buscarPorId(id).map(reserva -> {
            if (!reserva.getIdRestaurante().equals(restauranteId)) {
                return ResponseEntity.status(403).body("No autorizado para modificar esta reserva.");
            }
            reserva.setEstado(nuevoEstado);
            reservaService.actualizar(reserva);
            return ResponseEntity.ok("Estado actualizado a " + nuevoEstado);
        }).orElse(ResponseEntity.notFound().build());
    }
}
