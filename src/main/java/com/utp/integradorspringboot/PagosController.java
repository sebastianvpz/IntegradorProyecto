package com.utp.integradorspringboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PagosController {

    @RequestMapping("/pagos")
    public String page() {
        return "pagos";
    }
}
