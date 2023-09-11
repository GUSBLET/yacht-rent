package com.yachtrent.main.mail.service.dto;

import java.io.File;
import java.util.List;

public record MailMessageWithAttachmentFile(
        String reciverEmail,
        String text,
        String subject,
        List<File> files) { }
