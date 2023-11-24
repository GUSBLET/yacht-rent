package com.yachtrent.main.order.dto;

import com.yachtrent.main.account.Account;
import com.yachtrent.main.order.Order;
import com.yachtrent.main.order.OrderStatus;
import com.yachtrent.main.order.PaymentMethod;
import com.yachtrent.main.techniacal.mapper.Mapper;
import com.yachtrent.main.yacht.dto.YachtDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDto implements Mapper<OrderDto, Order> {

    private YachtDto yacht;
    private Account account;

    @NotBlank(message = "Enter your first name")
    private String firstName;

    @NotBlank(message = "Enter your last name")
    private String lastName;

    @NotBlank(message = "Enter your phone number")
    @Pattern(regexp = "^\\+380\\d{10}$", message = "Wrong phone number")
    @Size(max = 14, message = "Phone number must be at 10 characters long")
    private String phoneNumber;

    @NotBlank(message = "Enter your email")
    @Email(message = "Incorrect mail entry")
    private String email;

    @NotBlank(message = "Enter planned number of people")
    private String numberOfPeople;

    @NotBlank(message = "Сhoose the time by which you want to order a yacht")
    private String startOfRent;

    @NotBlank(message = "Select the approximate time at which you will rent a yacht")
    private String finishOfRent;

    @NotBlank(message = "Choose a date")
    private String date;

    private PaymentMethod paymentMethod;

    private float subtotal;

    private OrderStatus status;

    @Override
    public OrderDto toDto(Order entity) {
        return OrderDto.builder()
                .yacht(new YachtDto().toDto(entity.getYacht()))
                .subtotal(entity.getPrice())
                .status(entity.getStatus())
                .numberOfPeople(String.valueOf(entity.getNumberOfPersons()))
                .startOfRent(entity.getStartOfRent().toString())
                .finishOfRent(entity.getFinishOfRent().toString())
                .account(entity.getAccount())
                .date(entity.getDate().toString())
                .build();
    }

    @Override
    public Order toEntity(OrderDto dto) {
        return Order.builder()
                .price(dto.subtotal)
                .status(dto.status)
                .numberOfPersons(Integer.parseInt(dto.numberOfPeople))
                .startOfRent(LocalTime.parse(dto.startOfRent, DateTimeFormatter.ofPattern("HH:mm")))
                .finishOfRent(LocalTime.parse(dto.finishOfRent, DateTimeFormatter.ofPattern("HH:mm")))
                .date(LocalDate.parse(dto.date, DateTimeFormatter.ofPattern("MM/dd/yyyy")))
                .yacht(dto.getYacht().toEntity(dto.getYacht()))
                .account(dto.account)
                .paymentMethod(dto.paymentMethod)
                .build();
    }
}
