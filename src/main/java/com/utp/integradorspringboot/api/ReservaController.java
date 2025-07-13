package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.models.Reserva;
import com.utp.integradorspringboot.services.ReservaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@CrossOrigin
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    // Listar reservas (con o sin filtro de fecha)
    @GetMapping
    public List<Reserva> listarReservas(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            HttpSession session
    ) {
        Integer restauranteId = (Integer) session.getAttribute("restauranteId");
        return (fecha != null)
                ? reservaService.obtenerPorRestauranteYFecha(restauranteId, fecha)
                : reservaService.obtenerPorRestaurante(restauranteId);
    }

    // Crear reserva
    @PostMapping
    public Reserva crear(@RequestBody Reserva reserva, HttpSession session) {
        reserva.setFechaCreacion(LocalDate.now());
        reserva.setIdRestaurante((Integer) session.getAttribute("restauranteId"));
        return reservaService.guardar(reserva);
    }

    // Actualizar reserva
    @PutMapping("/{id}")
    public Reserva actualizar(@PathVariable Integer id, @RequestBody Reserva actualizada) {
        Reserva existente = reservaService.obtenerPorId(id);
        if (existente == null) return null;

        // Campos actualizables
        existente.setNombresComensal(actualizada.getNombresComensal());
        existente.setApellidosComensal(actualizada.getApellidosComensal());
        existente.setCorreoComensal(actualizada.getCorreoComensal());
        existente.setTelefonoComensal(actualizada.getTelefonoComensal());
        existente.setFechaReserva(actualizada.getFechaReserva());
        existente.setHoraReserva(actualizada.getHoraReserva());
        existente.setNumeroPersonas(actualizada.getNumeroPersonas());
        existente.setOcasion(actualizada.getOcasion());
        existente.setEstado(actualizada.getEstado());

        return reservaService.guardar(existente);
    }

    // Eliminar reserva
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        reservaService.eliminar(id);
    }

    // Marcar como completada
    @PutMapping("/completar/{id}")
    public Reserva completar(@PathVariable Integer id) {
        Reserva reserva = reservaService.obtenerPorId(id);
        if (reserva != null) {
            reserva.setEstado("completado");
            return reservaService.guardar(reserva);
        }
        return null;
    }
}
