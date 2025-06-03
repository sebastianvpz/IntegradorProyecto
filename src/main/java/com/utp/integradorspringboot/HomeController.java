/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.integradorspringboot;

import com.utp.integradorspringboot.models.Email;
import com.utp.integradorspringboot.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author jcerv
 */
@Controller
public class HomeController {
    @Autowired
    private JavaMailSender emailSender;
    @RequestMapping("/")
    public String page() {
        EmailService.SolicitarEnvio(
                new Email("u18307571@utp.edu.pe","Mensaje de prueba","Mensaje de prueba"),
                emailSender);
        return "home";
    }
}
