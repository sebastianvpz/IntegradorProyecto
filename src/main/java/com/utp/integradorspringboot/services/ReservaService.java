package com.utp.integradorspringboot.services;

import com.utp.integradorspringboot.models.Reserva;
import com.utp.integradorspringboot.repositories.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    public List<Reserva> obtenerPorRestaurante(Integer restauranteId) {
        return reservaRepository.findByIdRestaurante(restauranteId);
    }

    public List<Reserva> obtenerPorRestauranteYFecha(Integer restauranteId, LocalDate fecha) {
        return reservaRepository.findByIdRestauranteAndFechaReserva(restauranteId, fecha);
    }

    public Reserva guardar(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    public void eliminar(Integer id) {
        reservaRepository.deleteById(id);
    }

    public Reserva obtenerPorId(Integer id) {
        return reservaRepository.findById(id).orElse(null);
    }
}
