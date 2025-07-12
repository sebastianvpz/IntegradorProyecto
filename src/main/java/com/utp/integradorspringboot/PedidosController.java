package com.utp.integradorspringboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PedidosController {

    @RequestMapping("/pedidos")
    public String page() {
        return "pedidos";
    }
}
