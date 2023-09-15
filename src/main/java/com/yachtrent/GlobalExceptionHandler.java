package com.yachtrent;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.text.ParseException;

/*
* Контроллер который перехвативает все ошибки с помощью аннотации @ControllerAdvice
* Пока котнороллер возвращет страницу ошибок.
* Ошибки еще можно отправлять в виде json и возвращать тело HTTP ответа c татусом
* */

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MessagingException.class)
    public String getMessagingException(Model model) {
        model.addAttribute("error", "Oops, there was an error sending the sheet by email");
        log.error("Error sending email");
        return "globalError";
    }

    @ExceptionHandler(ParseException.class)
    public String getMParseException(Model model) {
        model.addAttribute("error", "Something went wrong, try ordering another time");
        log.error("Error creating order");
        return "globalError";
    }
}
