package com.yachtrent.main.account.dto;

import com.yachtrent.main.account.validator.PasswordMatch;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

@Data
@PasswordMatch(password = "password", passwordConfirm = "passwordConfirm")
@ToString
public class Password {
    private long id;

    @NotBlank(message = "Enter your password")
    @Size(min = 5, message = "Password must be at least 5 characters long")
    private String password;

    @NotBlank(message = "Confirm password")
    private String passwordConfirm;
}
