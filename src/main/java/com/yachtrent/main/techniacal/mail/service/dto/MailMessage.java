package com.yachtrent.main.techniacal.mail.service.dto;

public record MailMessage (
        String receiverEmail,
        String text,
        String subject
) { }
