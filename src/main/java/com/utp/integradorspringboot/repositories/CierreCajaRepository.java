package com.utp.integradorspringboot.repositories;

import com.utp.integradorspringboot.models.CierreCaja;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CierreCajaRepository extends JpaRepository<CierreCaja, Long> {
    Optional<CierreCaja> findFirstByEstado(String estado);
    Optional<CierreCaja> findFirstByEstadoOrderByFechaInicioDesc(String estado);
}
