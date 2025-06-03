/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.integradorspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author jcerv
 */
@Controller
public class ProfesoresController {
    @Autowired
    @RequestMapping("/profesores")
    public String page() {
        //model.addAttribute("attribute", "value");
        return "profesores";
    }
}
