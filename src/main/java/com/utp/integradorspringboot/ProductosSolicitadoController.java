package com.utp.integradorspringboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductosSolicitadoController {
    @RequestMapping("/productos-solicitados")
    public String mostrarProductosSolicitados() {
        return "productos-solicitados";
    }
}
