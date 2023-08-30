package com.yachtrent.domain.view.models;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SingInViewModel {
    @NotBlank(message = "Enter your login")
    public String login;
    @NotBlank(message = "Enter your password")
    public String password;
    public boolean rememberMe;
}
