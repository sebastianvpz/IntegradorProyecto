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
    private PagoRepository pagoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Calcula las métricas del dashboard para un restaurante, en vista mensual o anual.
     *
     * @param request Petición HTTP para extraer JWT y restauranteId.
     * @param vista   "mes" o "anio".
     * @param mes     Mes (1-12), usado solo si vista="mes".
     * @param anio    Año.
     * @return DTO con las métricas.
     */
    public DashboardDto obtenerDatosDashboard(HttpServletRequest request, String vista, Integer mes, Integer anio) {
        String token = jwtUtil.obtenerTokenDesdeRequest(request);
        Long restauranteId = jwtUtil.extraerRestauranteId(token);

        LocalDate hoy = LocalDate.now();
        int anioSeleccionado = (anio != null) ? anio : hoy.getYear();

        double egresosDelMes = 0.0; // Placeholder

        if ("mes".equalsIgnoreCase(vista)) {
            int mesSeleccionado = (mes != null) ? mes : hoy.getMonthValue();

            LocalDate inicioMes = LocalDate.of(anioSeleccionado, mesSeleccionado, 1);
            LocalDate finMes = inicioMes.withDayOfMonth(inicioMes.lengthOfMonth());

            int clientesDelMes = pedidoRepository.countClientesDistinctByRestauranteAndFecha(restauranteId, inicioMes, finMes);
            double ingresosDelMes = pagoRepository.sumPagosByRestauranteAndFecha(restauranteId, inicioMes, finMes);
            int ordenesDelMes = pedidoRepository.countByRestauranteAndFecha(restauranteId, inicioMes, finMes);

            Map<String, Integer> clientesPorMozo = new HashMap<>();
            for (Object[] row : pedidoRepository.countPedidosPorMozo(restauranteId)) {
                clientesPorMozo.put((String) row[0], ((Number) row[1]).intValue());
            }

            List<Double> ingresosPorMes = pagoRepository.sumPagosPorMesAnio(restauranteId, anioSeleccionado);
            List<Double> egresosPorMes = Arrays.asList(new Double[12]);
            Collections.fill(egresosPorMes, 0.0);

            return DashboardDto.builder()
                    .clientesDelMes(clientesDelMes)
                    .ingresosDelMes(ingresosDelMes)
                    .ordenesDelMes(ordenesDelMes)
                    .egresosDelMes(egresosDelMes)
                    .clientesPorMes2024(pedidoRepository.countClientesPorMes(restauranteId, 2024))
                    .clientesPorMes2025(pedidoRepository.countClientesPorMes(restauranteId, 2025))
                    .clientesPorMozo(clientesPorMozo)
                    .ingresosPorMes(ingresosPorMes)
                    .egresosPorMes(egresosPorMes)
                    .build();

        } else { // vista = "anio"
            List<Double> ingresosPorMes = pagoRepository.sumPagosPorMesAnio(restauranteId, anioSeleccionado);
            List<Double> egresosPorMes = Arrays.asList(new Double[12]);
            Collections.fill(egresosPorMes, 0.0);

            return DashboardDto.builder()
                    .clientesDelMes(0)
                    .ingresosDelMes(0)
                    .ordenesDelMes(0)
                    .egresosDelMes(0)
                    .clientesPorMes2024(pedidoRepository.countClientesPorMes(restauranteId, 2024))
                    .clientesPorMes2025(pedidoRepository.countClientesPorMes(restauranteId, 2025))
                    .clientesPorMozo(new HashMap<>())
                    .ingresosPorMes(ingresosPorMes)
                    .egresosPorMes(egresosPorMes)
                    .build();
        }
    }
}
