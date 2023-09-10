package com.yachtrent.main.dto.account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignInViewModel {

    @NotBlank(message = "Enter your email")
    @Email(message = "Incorrect mail entry")
    private String email;

    @NotBlank(message = "Enter your password")
    private String password;

    private boolean rememberMe;
}
