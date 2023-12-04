package com.yachtrent.main.account.dto;

import com.yachtrent.main.account.Account;
import com.yachtrent.main.role.Role;
import com.yachtrent.main.techniacal.mapper.Mapper;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EditAccount implements Mapper<EditAccount, Account> {

    private Long id;

    @NotBlank(message = "Enter your name")
    private String name;

    @NotBlank(message = "Enter your lastName")
    private String lastName;

    @Size(max = 300, message = "bio too big")
    private String bio;

    @NotBlank(message = "Enter your phone number")
    @Pattern(regexp = "^\\+380\\d{10}$", message = "Wrong phone number")
    @Size(max = 14, message = "Phone number must be at 10 characters long")
    private String phoneNumber;

    @NotBlank(message = "Enter your email")
    @Email(message = "Incorrect mail entry")
    private String email;

    @Size(min = 5, message = "Password must be at least 5 characters long")
    private String password;

    private Set<Role> roles;

    private boolean changeEmail;

    @Override
    public EditAccount toDto(Account entity) {
        return EditAccount.builder()
                .id(entity.getId())
                .password(entity.getPassword())
                .name(entity.getName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .bio(entity.getBio())
                .phoneNumber(entity.getPhoneNumber())
                .roles(entity.getRoles())
                .build();
    }

    @Override
    public Account toEntity(EditAccount dto) {
        return Account.builder()
                .id(dto.id)
                .password(dto.password)
                .name(dto.name)
                .lastName(dto.lastName)
                .email(dto.email)
                .bio(dto.bio)
                .phoneNumber(dto.phoneNumber)
                .roles(dto.roles)
                .build();
    }
}
