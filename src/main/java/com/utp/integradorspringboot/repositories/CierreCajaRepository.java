package com.utp.integradorspringboot.repositories;

import com.utp.integradorspringboot.models.CierreCaja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CierreCajaRepository extends JpaRepository<CierreCaja, Long> {
    Optional<CierreCaja> findFirstByEstado(String estado);
    Optional<CierreCaja> findFirstByEstadoOrderByFechaInicioDesc(String estado);

    @Query("""
        SELECT COALESCE(SUM(c.totalEgresos), 0)
        FROM CierreCaja c
        WHERE c.idRestaurante = :restauranteId
          AND c.fechaInicio BETWEEN :inicio AND :fin
    """)
    BigDecimal sumEgresosByRestauranteAndFecha(
            @Param("restauranteId") Long restauranteId,
            @Param("inicio") LocalDate inicio,
            @Param("fin") LocalDate fin);

    @Query("""
        SELECT MONTH(c.fechaInicio), COALESCE(SUM(c.totalEgresos), 0)
        FROM CierreCaja c
        WHERE c.idRestaurante = :restauranteId
          AND YEAR(c.fechaInicio) = :year
        GROUP BY MONTH(c.fechaInicio)
        ORDER BY MONTH(c.fechaInicio)
    """)
    List<Object[]> sumEgresosPorMesAnio(
            @Param("restauranteId") Long restauranteId,
            @Param("year") int year);

    @Query("""
        SELECT COALESCE(SUM(c.totalVentas), 0)
        FROM CierreCaja c
        WHERE c.idRestaurante = :restauranteId
          AND c.fechaInicio BETWEEN :inicio AND :fin
    """)
    BigDecimal sumIngresosByRestauranteAndFecha(
            @Param("restauranteId") Long restauranteId,
            @Param("inicio") LocalDate inicio,
            @Param("fin") LocalDate fin);

    @Query("""
        SELECT MONTH(c.fechaInicio), COALESCE(SUM(c.totalVentas), 0)
        FROM CierreCaja c
        WHERE c.idRestaurante = :restauranteId
          AND YEAR(c.fechaInicio) = :year
        GROUP BY MONTH(c.fechaInicio)
        ORDER BY MONTH(c.fechaInicio)
    """)
    List<Object[]> sumIngresosPorMesAnio(
            @Param("restauranteId") Long restauranteId,
            @Param("year") int year);



}
