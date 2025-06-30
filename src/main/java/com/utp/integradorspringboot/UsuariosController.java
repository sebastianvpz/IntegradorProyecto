package com.utp.integradorspringboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UsuariosController {
    @RequestMapping("/usuarios")
    public String mostrarUsuarios() {
        return "usuarios";
    }
}
