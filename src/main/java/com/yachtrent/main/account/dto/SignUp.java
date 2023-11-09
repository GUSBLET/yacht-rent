package com.yachtrent.main.account.dto;

import com.yachtrent.main.account.validator.PasswordMatch;
import com.yachtrent.main.role.Authority;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@PasswordMatch(password = "password", passwordConfirm = "passwordConfirm")
public class SignUp {

    @NotBlank(message = "Enter your name")
    private String name;

    @NotBlank(message = "Enter your lastName")
    private String lastName;

    @NotBlank(message = "Enter your phone number")
    private String phoneNumber;

    @NotBlank(message = "Enter your email")
    @Email(message = "Incorrect mail entry")
    private String email;

    @NotBlank(message = "Enter your password")
    @Size(min = 5, message = "Password must be at least 5 characters long.")
    private String password;

    @NotBlank(message = "Confirm password")
    private String passwordConfirm;

    @NotNull
    private Authority role;
}
