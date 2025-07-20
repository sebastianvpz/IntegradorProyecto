package com.utp.integradorspringboot.services;

import com.utp.integradorspringboot.dto.DashboardDto;
import com.utp.integradorspringboot.repositories.*;
import com.utp.integradorspringboot.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class DashboardService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private CierreCajaRepository cierreCajaRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public DashboardDto obtenerDatosDashboard(HttpServletRequest request, String vista, Integer mes, Integer anio) {
        String token = jwtUtil.obtenerTokenDesdeRequest(request);
        Long restauranteId = jwtUtil.extraerRestauranteId(token);

        LocalDate hoy = LocalDate.now();
        int anioSeleccionado = (anio != null) ? anio : hoy.getYear();

        double egresosDelMes = 0.0;
        double ingresosDelMes = 0.0;

        if ("mes".equalsIgnoreCase(vista)) {
            int mesSeleccionado = (mes != null) ? mes : hoy.getMonthValue();

            LocalDate inicioMes = LocalDate.of(anioSeleccionado, mesSeleccionado, 1);
            LocalDate finMes = inicioMes.withDayOfMonth(inicioMes.lengthOfMonth());

            int clientesDelMes = pedidoRepository.countClientesDistinctByRestauranteAndFecha(restauranteId, inicioMes, finMes);
            int ordenesDelMes = pedidoRepository.countByRestauranteAndFecha(restauranteId, inicioMes, finMes);

            Number egresosBD = cierreCajaRepository.sumEgresosByRestauranteAndFecha(restauranteId, inicioMes, finMes);
            egresosDelMes = egresosBD != null ? egresosBD.doubleValue() : 0.0;

            Number ingresosBD = cierreCajaRepository.sumIngresosByRestauranteAndFecha(restauranteId, inicioMes, finMes);
            ingresosDelMes = ingresosBD != null ? ingresosBD.doubleValue() : 0.0;

            Map<String, Integer> clientesPorMozo = new HashMap<>();
            for (Object[] row : pedidoRepository.countPedidosPorMozoEnRango(restauranteId, inicioMes, finMes)) {
                clientesPorMozo.put((String) row[0], ((Number) row[1]).intValue());
            }

            List<Double> ingresosPorMes = Arrays.asList(new Double[12]);
            Collections.fill(ingresosPorMes, 0.0);

            for (Object[] row : cierreCajaRepository.sumIngresosPorMesAnio(restauranteId, anioSeleccionado)) {
                Integer mesQuery = (Integer) row[0];
                Double monto = ((Number) row[1]).doubleValue();
                ingresosPorMes.set(mesQuery - 1, monto);
            }

            List<Double> egresosPorMes = Arrays.asList(new Double[12]);
            Collections.fill(egresosPorMes, 0.0);

            for (Object[] row : cierreCajaRepository.sumEgresosPorMesAnio(restauranteId, anioSeleccionado)) {
                Integer mesQuery = (Integer) row[0];
                Double monto = ((Number) row[1]).doubleValue();
                egresosPorMes.set(mesQuery - 1, monto);
            }

            List<Integer> clientesPorMes2024 = Arrays.asList(new Integer[12]);
            Collections.fill(clientesPorMes2024, 0);

            List<Object[]> clientesQuery2024 = pedidoRepository.countClientesPorMes(restauranteId, 2024);
            for (Object[] row : clientesQuery2024) {
                Integer mesQuery = (Integer) row[0];
                Integer total = ((Number) row[1]).intValue();
                clientesPorMes2024.set(mesQuery - 1, total);
            }

            List<Integer> clientesPorMes2025 = Arrays.asList(new Integer[12]);
            Collections.fill(clientesPorMes2025, 0);

            List<Object[]> clientesQuery2025 = pedidoRepository.countClientesPorMes(restauranteId, 2025);
            for (Object[] row : clientesQuery2025) {
                Integer mesQuery = (Integer) row[0];
                Integer total = ((Number) row[1]).intValue();
                clientesPorMes2025.set(mesQuery - 1, total);
            }

            return DashboardDto.builder()
                    .clientesDelMes(clientesDelMes)
                    .ingresosDelMes(ingresosDelMes)
                    .ordenesDelMes(ordenesDelMes)
                    .egresosDelMes(egresosDelMes)
                    .clientesPorMes2024(clientesPorMes2024)
                    .clientesPorMes2025(clientesPorMes2025)
                    .clientesPorMozo(clientesPorMozo)
                    .ingresosPorMes(ingresosPorMes)
                    .egresosPorMes(egresosPorMes)
                    .build();

        } else {
            // vista anual
            List<Double> ingresosPorMes = Arrays.asList(new Double[12]);
            Collections.fill(ingresosPorMes, 0.0);

            for (Object[] row : cierreCajaRepository.sumIngresosPorMesAnio(restauranteId, anioSeleccionado)) {
                Integer mesQuery = (Integer) row[0];
                Double monto = ((Number) row[1]).doubleValue();
                ingresosPorMes.set(mesQuery - 1, monto);
            }

            List<Double> egresosPorMes = Arrays.asList(new Double[12]);
            Collections.fill(egresosPorMes, 0.0);

            for (Object[] row : cierreCajaRepository.sumEgresosPorMesAnio(restauranteId, anioSeleccionado)) {
                Integer mesQuery = (Integer) row[0];
                Double monto = ((Number) row[1]).doubleValue();
                egresosPorMes.set(mesQuery - 1, monto);
            }

            List<Integer> clientesPorMes2024 = Arrays.asList(new Integer[12]);
            Collections.fill(clientesPorMes2024, 0);

            List<Object[]> clientesQuery2024 = pedidoRepository.countClientesPorMes(restauranteId, 2024);
            for (Object[] row : clientesQuery2024) {
                Integer mesQuery = (Integer) row[0];
                Integer total = ((Number) row[1]).intValue();
                clientesPorMes2024.set(mesQuery - 1, total);
            }

            List<Integer> clientesPorMes2025 = Arrays.asList(new Integer[12]);
            Collections.fill(clientesPorMes2025, 0);

            List<Object[]> clientesQuery2025 = pedidoRepository.countClientesPorMes(restauranteId, 2025);
            for (Object[] row : clientesQuery2025) {
                Integer mesQuery = (Integer) row[0];
                Integer total = ((Number) row[1]).intValue();
                clientesPorMes2025.set(mesQuery - 1, total);
            }

            return DashboardDto.builder()
                    .clientesDelMes(0)
                    .ingresosDelMes(0)
                    .ordenesDelMes(0)
                    .egresosDelMes(0)
                    .clientesPorMes2024(clientesPorMes2024)
                    .clientesPorMes2025(clientesPorMes2025)
                    .clientesPorMozo(new HashMap<>())
                    .ingresosPorMes(ingresosPorMes)
                    .egresosPorMes(egresosPorMes)
                    .build();
        }
    }
}
