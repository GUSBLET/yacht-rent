package com.yachtrent.main.mail.service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


@Service
public class MailService {

    public boolean sendMailByEmail(String reciverEmail){
        Properties properties = new Properties();

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.err.println("Unable to find config.properties");
                return false; // Handle the error as needed
            }

            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Handle the error as needed
        }

        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.host", properties.getProperty("spring.mail.host"));
        mailProperties.put("mail.smtp.port", properties.getProperty("spring.mail.port")); // SMTP port (587 for TLS, 465 for SSL, 25 for non-secure)
        mailProperties.put("mail.smtp.auth", true);
        mailProperties.put("mail.smtp.starttls.enable", true); // Use TLS;

        String email = properties.getProperty("spring.mail.username");
        String password = properties.getProperty("spring.mail.password");

        Session session = Session.getInstance(mailProperties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });

        try {
            // Создание сообщения
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email)); // Ваш адрес отправителя
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(reciverEmail)); // Адрес получателя
            message.setSubject("Тема письма");
            message.setText("Текст письма");

            // Отправка сообщения
            Transport.send(message);

            System.out.println("Письмо успешно отправлено.");

        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Ошибка отправки письма: " + e.getMessage());
        }

        return  true;
    }
}
