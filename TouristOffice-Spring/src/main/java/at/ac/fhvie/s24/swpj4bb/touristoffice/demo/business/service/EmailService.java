package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Properties;
import java.net.URL;


@Service
public class EmailService {

    Authenticator authenticator;
    Properties props;

    public EmailService() {
        props = new Properties();
        URL smtpUrl = ClassLoader.getSystemResource("smtp.properties");

        try  {
            props.load(smtpUrl.openStream());
        } catch (IOException fie) {
            fie.printStackTrace();
        }

        authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                Properties smtpUserProps = new Properties();
                URL smtpUserURL = ClassLoader.getSystemResource("smtpuser.properties");

                try  {
                    smtpUserProps.load(smtpUserURL.openStream());
                } catch (IOException fie) {
                    fie.printStackTrace();
                }

                System.out.println(props.getProperty("mail.smtp.host"));

                return new PasswordAuthentication(smtpUserProps.getProperty("username"),
                        smtpUserProps.getProperty("password"));
            }
        };
    }

    public void sendSimpleMessage(String to, String subject, String text) {

    }

    public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) {
        System.out.println(props.getProperty("mail.smtp.host"));
        Session session = Session.getInstance(props, authenticator);
        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress("hotelstatistics01@gmail.com"));

            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            message.setSubject(subject);

            BodyPart messageBodyPart = new MimeBodyPart();

            messageBodyPart.setText(text);

            Multipart multipart = new MimeMultipart();
            // Text message
            multipart.addBodyPart(messageBodyPart);
            // Attachment
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(pathToAttachment);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName("occupancy.pdf");
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);

            Transport.send(message);
            System.out.println("Email Message Sent Successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}

