package com.utp.integradorspringboot.repositories;

import com.utp.integradorspringboot.models.LoteProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoteProductoRepository extends JpaRepository<LoteProducto, Long> {
    List<LoteProducto> findByEstadoAndIdRestaurante(String estado, Long restauranteId);
    List<LoteProducto> findByProductoIdAndIdRestauranteAndEstadoOrderByFechaVencimientoAsc(
            Long productoId,
            Long idRestaurante,
            String estado
    );
    List<LoteProducto> findByProductoIdAndIdRestaurante(Long productoId, Long restauranteId);


}
