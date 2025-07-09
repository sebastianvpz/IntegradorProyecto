package com.utp.integradorspringboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PerfilController {
    @RequestMapping("/perfil")
    public String mostrarPerfil() {
        return "perfil";
    }
}
