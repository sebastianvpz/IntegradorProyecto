package com.utp.integradorspringboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GestionZonasController {
    @RequestMapping("/pisos")
    public String mostrarGestionZonas() {
        return "gestion_zonas";
    }
}
