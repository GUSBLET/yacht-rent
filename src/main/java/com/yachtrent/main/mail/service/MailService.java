package com.yachtrent.main.mail.service;

import com.yachtrent.main.mail.service.dto.MailMessage;
import com.yachtrent.main.mail.service.dto.MailMessageHTML;
import com.yachtrent.main.mail.service.dto.MailMessageWithAttachmentFile;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Properties;


@Service
public class MailService {


    @Value("${spring.mail.username}")
    private  String email;

    @Value("${spring.mail.password}")
    private  String password;

    @Value("${spring.mail.host}")
    private  String host;

    @Value("${spring.mail.port}")
    private  String port;

    // Send mail message with text.
    public boolean sendMail(MailMessage mailMassage) {
        Session session = getSession();

        try {
            // Create massage.
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailMassage.reciverEmail()));
            message.setSubject(mailMassage.subject());
            message.setText(mailMassage.text());

            // Send massage.
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // Send mail message with html file.
    public boolean sendMail(MailMessageHTML parametters) {
        Session session = getSession();

        try {
            // Create massage.
            String html = new String(Files.readAllBytes(parametters.htmlFile().toPath()));
            Multipart multipart = new MimeMultipart();

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(html, "text/html; charset=utf-8");
            multipart.addBodyPart(mimeBodyPart);

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(parametters.reciverEmail()));
            message.setSubject(parametters.subject());
            message.setContent(multipart);


            // Send massage.
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    // Send mail message with attachment files.
    public boolean sendMail(MailMessageWithAttachmentFile parametters) {
        Session session = getSession();

        try {
            // Create massage.
            Multipart multipart = new MimeMultipart();
            for (File file : parametters.files()) {
                MimeBodyPart attachmentPart = new MimeBodyPart();
                attachmentPart.attachFile(file);

                multipart.addBodyPart(attachmentPart);
            }

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(parametters.reciverEmail()));
            message.setSubject(parametters.subject());
            message.setText(parametters.text());
            message.setContent(multipart);


            // Send massage.
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }



    private Session getSession() {
        // Create properties for smtp client.
        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.host", host);
        mailProperties.put("mail.smtp.port", port); // SMTP port (587 for TLS, 465 for SSL, 25 for non-secure)
        mailProperties.put("mail.smtp.auth", true);
        mailProperties.put("mail.smtp.starttls.enable", true); // Use TLS;

        // Create session by properties.
        return Session.getInstance(mailProperties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });
    }
}
