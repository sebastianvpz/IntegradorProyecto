package com.utp.integradorspringboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NuevasPasswordController {
    @RequestMapping("/nueva-password")
    public String mostrarNuevaPassword() {
        return "nueva-password";
    }
}
