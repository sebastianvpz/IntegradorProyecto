package com.utp.integradorspringboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CajaVistaController {
    @RequestMapping("/caja")
    public String mostrarCaja() {
        return "caja";
    }
}
