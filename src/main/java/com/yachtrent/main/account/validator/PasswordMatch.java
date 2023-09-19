package com.yachtrent.main.account.validator;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({ TYPE })
@Constraint(validatedBy = PasswordMatchValidator.class)
public @interface PasswordMatch {
    //сообщение об ошибке
    String message() default "Password mismatch";

    //представляет группу ограничений
    Class<?>[] groups() default {};

    //дополнетельная информации
    Class<? extends Payload>[] payload() default {};

    //сюда буду вписывать название полей для сравнения
    String passwordConfirm();
    String password();
}

