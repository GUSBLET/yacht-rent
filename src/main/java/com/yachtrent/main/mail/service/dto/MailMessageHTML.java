package com.yachtrent.main.mail.service.dto;

import java.io.File;

public record MailMessageHTML(
        String reciverEmail,
        File htmlFile,
        String subject) {
}
