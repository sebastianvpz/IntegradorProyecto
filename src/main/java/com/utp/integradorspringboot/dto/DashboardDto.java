package com.utp.integradorspringboot.dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardDto {

    private int clientesDelMes;
    private double ingresosDelMes;
    private int ordenesDelMes;
    private double egresosDelMes;

    private List<Integer> clientesPorMes2024;
    private List<Integer> clientesPorMes2025;

    private Map<String, Integer> clientesPorMozo;

    private List<Double> ingresosPorMes;
    private List<Double> egresosPorMes;
}
