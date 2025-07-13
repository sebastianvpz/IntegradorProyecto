package com.utp.integradorspringboot.services;

import com.utp.integradorspringboot.models.Reserva;
import com.utp.integradorspringboot.repositories.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    public List<Reserva> listarPorRestaurante(Integer restauranteId) {
        return reservaRepository.findByIdRestaurante(restauranteId);
    }

    public Optional<Reserva> buscarPorId(Integer id) {
        return reservaRepository.findById(id);
    }

    public Reserva guardar(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    public Reserva actualizar(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    public void eliminar(Integer id) {
        reservaRepository.deleteById(id);
    }
}
