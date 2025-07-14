package com.utp.integradorspringboot.repositories;

import com.utp.integradorspringboot.models.DetalleMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleMovimientoRepository extends JpaRepository<DetalleMovimiento, Long> {
}
