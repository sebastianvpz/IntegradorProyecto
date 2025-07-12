package com.utp.integradorspringboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MesasController {

    @RequestMapping("/mesas")
    public String page() {
        return "mesas";
    }
}
