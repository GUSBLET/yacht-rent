package com.yachtrent.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignUpViewModel {
    @NotBlank(message = "Enter email")
    private String email;
    @NotBlank(message = "Enter login")
    private String login;
    @NotBlank(message = "Enter password")
    private String password;
    @NotBlank(message = "Confirm password")
    private String passwordConfirm;
}
