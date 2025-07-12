package com.utp.integradorspringboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controlador solo para servir la vista Thymeleaf de reservas del administrador.
 * La lógica CRUD se maneja por API REST (/api/reservas).
 */
@Controller
@RequestMapping("/admin/reservas")
public class ReservaAdminController {

    @GetMapping
    public String mostrarVistaReservas() {
        return "reservas"; // Este es el archivo reservas.html
    }
}
