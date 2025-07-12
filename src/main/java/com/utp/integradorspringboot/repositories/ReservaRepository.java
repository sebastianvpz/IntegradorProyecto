package com.utp.integradorspringboot.repositories;

import com.utp.integradorspringboot.models.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    List<Reserva> findByIdRestaurante(Integer idRestaurante);
    List<Reserva> findByIdRestauranteAndFechaReserva(Integer idRestaurante, LocalDate fechaReserva);
}
