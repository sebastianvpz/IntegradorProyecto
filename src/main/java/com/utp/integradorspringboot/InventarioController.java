package com.utp.integradorspringboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class InventarioController {

    @RequestMapping("/inventario")
    public String mostrarInventario() {
        return "inventario";
    }
}
