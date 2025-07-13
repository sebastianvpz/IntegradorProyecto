package com.utp.integradorspringboot.repositories;

import com.utp.integradorspringboot.models.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestauranteRepository extends JpaRepository<Restaurante, Integer> {
}
