package com.utp.integradorspringboot.repositories;

import com.utp.integradorspringboot.models.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    // Tú ya tienes:
    List<Reserva> findByEstado(String estado);

    // Si en el futuro quieres filtrar, podrías agregar:
    // List<Reserva> findByNombresComensalContainingIgnoreCaseOrFechaReserva(String nombre, String fecha);
}
