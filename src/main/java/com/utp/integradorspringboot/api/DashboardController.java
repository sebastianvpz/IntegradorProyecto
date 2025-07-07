package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.dto.DashboardDto;
import com.utp.integradorspringboot.services.DashboardService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para el dashboard principal.
 * Permite obtener las métricas principales en vista mensual o anual.
 */
@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    /**
     * Devuelve las métricas del dashboard.
     *
     * @param vista   Tipo de vista: "mes" o "anio". Por defecto "mes".
     * @param mes     Número de mes (1-12). Requerido si vista="mes".
     * @param anio    Año (ej: 2025). Opcional, usa año actual si no se especifica.
     * @param request Petición HTTP para extraer JWT.
     * @return DTO con las métricas del dashboard.
     */
    @GetMapping
    public DashboardDto obtenerDashboard(
            @RequestParam(value = "vista", defaultValue = "mes") String vista,
            @RequestParam(value = "mes", required = false) Integer mes,
            @RequestParam(value = "anio", required = false) Integer anio,
            HttpServletRequest request) {
        return dashboardService.obtenerDatosDashboard(request, vista, mes, anio);
    }
}
