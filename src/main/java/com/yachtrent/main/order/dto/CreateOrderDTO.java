package com.yachtrent.main.order.dto;

import com.yachtrent.main.yacht.Yacht;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateOrderDTO {
    @NotBlank(message = "Enter your full name")
    @Size(max = 30)
    private String customerName;

    @NotBlank(message = "Enter your last name")
    @Size(max = 30)
    private String customerLastName;

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

    private Yacht yacht = new Yacht();
}