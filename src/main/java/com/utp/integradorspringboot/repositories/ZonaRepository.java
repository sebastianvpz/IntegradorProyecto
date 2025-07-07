package com.utp.integradorspringboot.repositories;

import com.utp.integradorspringboot.models.Zona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ZonaRepository extends JpaRepository<Zona, Long> {
    List<Zona> findByRestauranteId(Long restauranteId);
}
