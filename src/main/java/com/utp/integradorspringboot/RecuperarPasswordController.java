package com.utp.integradorspringboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RecuperarPasswordController {
    @RequestMapping("/recuperar")
    public String mostrarRecuperar() {
        return "recuperar";
    }
}
