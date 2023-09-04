package com.yachtrent.domain.view.models.order;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;


@Data
public class CreateOrderViewModel {
    @NotBlank(message = "Enter your full name")
    @Size(max = 50)
    private String customerName;

    @NotBlank(message = "Enter your phone number")
    @Size(max = 20)
    private String customerPhoneNumber;

    @Size(max = 50)
    @Email(message = "Enter your email")
    private String customerEmail;

    private Float price;

    @NotBlank(message = "Enter your date of start rent")
    private String dateOfStart;

    @NotBlank(message = "Enter your date of finish rent")
    private String dateOfFinish;

    @NotBlank(message = "Enter your hour of start rent")
    private String hourOfStart;

    @NotBlank(message = "Enter your hour of finish rent")
    private String hourOfFinish;
}
