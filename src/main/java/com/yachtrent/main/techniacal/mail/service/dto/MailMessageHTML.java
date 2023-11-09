package com.yachtrent.main.techniacal.mail.service.dto;

import java.io.File;

public record MailMessageHTML (
        String receiverEmail,
        File htmlFile,
        String subject
) { }
