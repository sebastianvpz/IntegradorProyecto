package com.utp.integradorspringboot.repositories;

import com.utp.integradorspringboot.models.ProductoSolicitado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoSolicitadoRepository extends JpaRepository<ProductoSolicitado, Long> {
    List<ProductoSolicitado> findByEstadoAndProducto_IdRestaurante(String estado, Long restauranteId);
    List<ProductoSolicitado> findByRestauranteId(Long restauranteId);
}

