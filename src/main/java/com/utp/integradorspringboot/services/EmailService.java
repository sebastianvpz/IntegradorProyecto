/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.integradorspringboot.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import com.utp.integradorspringboot.models.Email;
/**
 *
 * @author jcerv_pm92n0w
 */
public class EmailService {
    public static void SolicitarEnvio(Email email, JavaMailSender emailSender) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("jcervanteslivon@gmail.com");
            message.setTo(email.getTo());
            message.setSubject(email.getSubject());
            message.setText(email.getMessage());
            emailSender.send(message);
        } catch (Exception e) {  
        }
    }
}
