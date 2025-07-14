package com.utp.integradorspringboot.repositories;

import com.utp.integradorspringboot.models.Pedido;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByRestauranteId(Long restauranteId);

    Optional<Pedido> findByIdAndRestauranteId(Long id, Long restauranteId);


    @Query("""
        SELECT COUNT(DISTINCT p.comensal)
        FROM Pedido p
        WHERE p.restauranteId = :restauranteId
          AND DATE(p.fechaCreacion) BETWEEN :inicio AND :fin
    """)
    int countClientesDistinctByRestauranteAndFecha(
            @Param("restauranteId") Long restauranteId,
            @Param("inicio") LocalDate inicio,
            @Param("fin") LocalDate fin);

    @Query("""
        SELECT COUNT(p)
        FROM Pedido p
        WHERE p.restauranteId = :restauranteId
          AND DATE(p.fechaCreacion) BETWEEN :inicio AND :fin
    """)
    int countByRestauranteAndFecha(
            @Param("restauranteId") Long restauranteId,
            @Param("inicio") LocalDate inicio,
            @Param("fin") LocalDate fin);

    @Query("""
        SELECT COUNT(p)
        FROM Pedido p
        WHERE p.restauranteId = :restauranteId
          AND MONTH(p.fechaCreacion) = :mes
          AND YEAR(p.fechaCreacion) = :year
    """)
    int countPedidosPorMesYAnio(
            @Param("restauranteId") Long restauranteId,
            @Param("mes") int mes,
            @Param("year") int year);

    @Query("""
        SELECT MONTH(p.fechaCreacion), COUNT(DISTINCT p.comensal)
        FROM Pedido p
        WHERE p.restauranteId = :restauranteId
          AND YEAR(p.fechaCreacion) = :year
        GROUP BY MONTH(p.fechaCreacion)
        ORDER BY MONTH(p.fechaCreacion)
    """)
    List<Object[]> countClientesPorMes(
            @Param("restauranteId") Long restauranteId,
            @Param("year") int year);


    @Query("""
        SELECT u.nombre AS mozo, COUNT(p) AS total
        FROM Pedido p
        JOIN Usuario u ON u.id = p.usuarioId
        WHERE p.restauranteId = :restauranteId
        GROUP BY u.nombre
    """)
    List<Object[]> countPedidosPorMozo(
            @Param("restauranteId") Long restauranteId);
}
