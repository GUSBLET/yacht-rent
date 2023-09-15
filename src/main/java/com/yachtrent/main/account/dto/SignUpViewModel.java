package com.yachtrent.main.account.dto;

import com.yachtrent.main.role.Authority;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignUpViewModel {

    @NotBlank(message = "Enter your email")
    @Email(message = "Incorrect mail entry")
    private String email;

    @NotBlank(message = "Enter your password")
    private String password;

    @NotBlank(message = "Confirm password")
    private String passwordConfirm;

    @NotNull
    private Authority role;
}
