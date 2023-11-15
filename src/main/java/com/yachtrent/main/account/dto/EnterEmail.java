package com.yachtrent.main.account.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@ToString
public class EnterEmail {

    @NotBlank(message = "Enter your email")
    @Email(message = "Incorrect mail entry")
    private String email;
}
