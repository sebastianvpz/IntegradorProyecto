package com.utp.integradorspringboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GraficosController {
    @RequestMapping("/dashboard")
    public String mostrarDashboard() {
        return "dashboard";
    }
}
