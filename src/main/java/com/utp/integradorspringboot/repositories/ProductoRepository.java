package com.utp.integradorspringboot.repositories;

import com.utp.integradorspringboot.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByEstado(String estado);
    List<Producto> findByEstadoAndIdRestaurante(String estado, Long restauranteId);

    Optional<Producto> findByIdAndIdRestaurante(Long productoId, Long restauranteId);
}