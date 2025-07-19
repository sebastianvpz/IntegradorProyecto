package com.utp.integradorspringboot.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.from}")
    private String from;

    public void enviarCorreoRecuperacion(String destinatario, String enlace) {
        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setFrom(from);
            helper.setTo(destinatario);
            helper.setSubject("Recuperación de contraseña");
            helper.setText(
                    "<p>Has solicitado recuperar tu contraseña.</p>" +
                            "<p>Haz clic en el siguiente enlace para establecer una nueva contraseña:</p>" +
                            "<p><a href=\"" + enlace + "\">Recuperar contraseña</a></p>" +
                            "<br><p>Si no solicitaste esto, puedes ignorar este correo.</p>",
                    true);

            mailSender.send(mensaje);
        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar correo de recuperación", e);
        }
    }
}