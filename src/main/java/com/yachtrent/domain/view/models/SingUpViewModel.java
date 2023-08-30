package com.yachtrent.domain.view.models;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SingUpViewModel {
    @NotBlank(message = "Enter email")
    public String email;
    @NotBlank(message = "Enter login")
    public String login;
    @NotBlank(message = "Enter password")
    public String password;
    @NotBlank(message = "Confirm password")
    public String passwordConfirm;
}
