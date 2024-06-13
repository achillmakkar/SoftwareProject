package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Properties;
import java.net.URL;

// CodeAnfang Nikola Button for Monthly Subscription 25.05.2024
@Service
public class EmailService {

    private final Authenticator authenticator;
    private final Properties props = new Properties();

    // CodeAnfang_Achill_FixesForJarFile_SMTP_was_not_working_05.06.2024
    public EmailService(@Value("classpath:smtp.properties") Resource smtpPropertiesResource,
                        @Value("classpath:smtpuser.properties") Resource smtpUserPropertiesResource) {
        try {
            props.load(smtpPropertiesResource.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load SMTP properties", e);
        }


        authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                Properties smtpUserProps = new Properties();
                try {
                    smtpUserProps.load(smtpUserPropertiesResource.getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("Failed to load SMTP user properties", e);
                }

                return new PasswordAuthentication(smtpUserProps.getProperty("username"),
                        smtpUserProps.getProperty("password"));
            }
        };
    }
    // CodeEnde_Achill_FixesForJarFile_SMTP_was_not_working_05.06.2024

    public void sendSimpleMessage(String to, String subject, String text) {

    }

    public String sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment, String filename) {
        System.out.println(props.getProperty("mail.smtp.host"));
        Session session = Session.getInstance(props, authenticator);
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("hotelstatistics01@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(text);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(pathToAttachment);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);
            Transport.send(message);
            System.out.println("Email Message Sent Successfully");
            return "E-Mail wurde erfolgreich gesendet";
        } catch (MessagingException e) {
            return "Fehler: " + e.getMessage();
        }
    }
}

// CodeEnde Nikola Button for Monthly Subscription 25.05.2024