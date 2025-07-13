package com.utp.integradorspringboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class ReservaMozoController {

    @RequestMapping("/reservas-mozo")
    public String mostrarVistaReservas() {
        return "reservas";
    }
}
