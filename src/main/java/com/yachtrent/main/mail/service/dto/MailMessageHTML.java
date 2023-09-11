package com.yachtrent.main.mail.service.dto;

import java.io.File;

public record MailMessageHTML(
        String receiverEmail,
        File htmlFile,
        String subject) {
}
