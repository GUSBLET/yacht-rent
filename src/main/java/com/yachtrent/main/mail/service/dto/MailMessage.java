package com.yachtrent.main.mail.service.dto;

public record MailMessage(
        String reciverEmail,
        String text,
        String subject) { }
