package com.yachtrent.main.dto.account;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignInViewModel {
    @NotBlank(message = "Enter your login")
    private String login;
    @NotBlank(message = "Enter your password")
    private String password;
    private boolean rememberMe;
}
