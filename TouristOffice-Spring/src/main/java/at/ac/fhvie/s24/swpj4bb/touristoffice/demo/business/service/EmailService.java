package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.service;
/*
import java.io.File;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            File file = new File(pathToAttachment);
            helper.addAttachment(file.getName(), file);
            emailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} */