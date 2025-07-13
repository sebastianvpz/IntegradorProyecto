package com.utp.integradorspringboot;

import com.utp.integradorspringboot.models.Reserva;
import com.utp.integradorspringboot.models.Restaurante;
import com.utp.integradorspringboot.repositories.ReservaRepository;
import com.utp.integradorspringboot.repositories.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Controller
public class ComensalController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @GetMapping("/comensal/{idRestaurante}")
    public String mostrarVistaComensal(@PathVariable Integer idRestaurante, Model model) {
        Restaurante restaurante = restauranteRepository.findById(idRestaurante).orElse(null);
        if (restaurante == null) {
            return "error/404";
        }
        model.addAttribute("restaurante", restaurante);
        model.addAttribute("reserva", new Reserva());
        return "comensal";
    }


    @PostMapping("/comensal/reservar")
    public String procesarReserva(@ModelAttribute Reserva reserva, Model model) {
        reserva.setFechaCreacion(LocalDate.now().toString());
        reserva.setEstado("reservado");
        reservaRepository.save(reserva);

        Restaurante restaurante = restauranteRepository.findById(reserva.getIdRestaurante()).orElse(null);
        model.addAttribute("restaurante", restaurante);
        model.addAttribute("mensaje", "¡Reserva enviada con éxito!");

        return "comensal";
    }
}
