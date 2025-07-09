package com.utp.integradorspringboot.repositories;

import com.utp.integradorspringboot.models.Mesa;
import com.utp.integradorspringboot.models.Zona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MesaRepository extends JpaRepository<Mesa, Long> {
    List<Mesa> findByZona(Zona zona);
}
