package com.utp.integradorspringboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class InventarioChefController {
    
    @RequestMapping("/inventariochef")
    public String mostrarInventarioChef() {
        return "inventario_chef";
    }
    
}
