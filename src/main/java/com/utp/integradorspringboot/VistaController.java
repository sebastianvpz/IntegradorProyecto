package com.utp.integradorspringboot.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VistaController {

    @GetMapping("/nuevospedidos")
    public String verNuevoPedido() {
        return "nuevospedidos"; // Thymeleaf buscará templates/nuevospedidos.html
    }

    @GetMapping("/pedidos")
    public String verPedidos() {
        return "pedidos"; // Si tienes pedidos.html, también lo puede servir
    }
}