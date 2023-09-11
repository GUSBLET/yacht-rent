package com.yachtrent.main.mail.service.dto;

public record MailMessage(
        String receiverEmail,
        String text,
        String subject) { }
