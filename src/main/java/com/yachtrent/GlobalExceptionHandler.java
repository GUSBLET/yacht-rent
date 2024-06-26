package com.yachtrent;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.thymeleaf.exceptions.TemplateInputException;

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
        model.addAttribute("error", "Oops, there was an err orsending the sheet by email");
        log.error("Error sending email");
        return "errors/globalError";
    }

    @ExceptionHandler(ParseException.class)
    public String getMParseException(Model model) {
        model.addAttribute("error", "Something went wrong, try ordering another time");
        log.error("Error creating order");
        return "errors/globalError";
    }

    @ExceptionHandler(TemplateInputException.class)
    public String getTemplateInputException(Model model) {
        model.addAttribute("error", "oh, for some reason the page won't load");
        log.error("Error template parsing:" );
        return "errors/globalError";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String getTIllegalArgumentException(Model model) {
        model.addAttribute("error", "oh, for some reason the page won't load");
        log.error("Error template parsing:" );
        return "errors/globalError";
    }

    @ExceptionHandler(SpelEvaluationException.class)
    public String getSpelEvaluationException(Model model) {
        model.addAttribute("error", "oh, for some reason the page won't load " + SpelEvaluationException.class);
        log.error("Error template parsing:" );
        return "errors/globalError";
    }
}
