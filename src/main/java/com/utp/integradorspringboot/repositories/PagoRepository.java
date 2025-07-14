package com.utp.integradorspringboot.repositories;

import com.utp.integradorspringboot.models.Pago;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Long> {

    @Query("""
        SELECT COALESCE(SUM(p.monto), 0)
        FROM Pago p
        JOIN Pedido ped ON p.pedidoId = ped.id
        WHERE ped.restauranteId = :restauranteId
          AND DATE(p.fechaPago) BETWEEN :inicio AND :fin
    """)
    double sumPagosByRestauranteAndFecha(
            @Param("restauranteId") Long restauranteId,
            @Param("inicio") LocalDate inicio,
            @Param("fin") LocalDate fin);

    @Query("""
        SELECT COALESCE(SUM(p.monto), 0)
        FROM Pago p
        JOIN Pedido ped ON p.pedidoId = ped.id
        WHERE ped.restauranteId = :restauranteId
          AND MONTH(p.fechaPago) = :mes
          AND YEAR(p.fechaPago) = :year
    """)
    double sumPagosPorMesYAnio(
            @Param("restauranteId") Long restauranteId,
            @Param("mes") int mes,
            @Param("year") int year);

    /**
     * Devuelve la suma de pagos agrupados por mes para un año dado,
     * incluyendo el número del mes y el monto total por mes.
     *
     * @param restauranteId id del restaurante
     * @param year año de consulta
     * @return lista de Object[] donde:
     *         [0] = Integer (número de mes: 1-12)
     *         [1] = Double (monto total del mes)
     */
    @Query("""
        SELECT MONTH(p.fechaPago), COALESCE(SUM(p.monto), 0)
        FROM Pago p
        JOIN Pedido ped ON p.pedidoId = ped.id
        WHERE ped.restauranteId = :restauranteId
          AND YEAR(p.fechaPago) = :year
        GROUP BY MONTH(p.fechaPago)
        ORDER BY MONTH(p.fechaPago)
    """)
    List<Object[]> sumPagosPorMesAnio(
            @Param("restauranteId") Long restauranteId,
            @Param("year") int year);
}
