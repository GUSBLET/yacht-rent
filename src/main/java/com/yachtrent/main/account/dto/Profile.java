package com.yachtrent.main.account.dto;

import com.yachtrent.main.account.Account;
import com.yachtrent.main.account.validator.PasswordMatch;
import com.yachtrent.main.role.Role;
import com.yachtrent.main.techniacal.mapper.Mapper;
import com.yachtrent.main.yacht.Yacht;
import com.yachtrent.main.yacht.dto.YachtDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Data
@Builder
@PasswordMatch(password = "password", passwordConfirm = "passwordConfirm")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Profile implements Mapper<Profile, Account> {

    private Long id;

    @NotBlank(message = "Enter your name")
    private String name;

    @NotBlank(message = "Enter your lastName")
    private String lastName;

    @NotBlank(message = "Enter your phone number")
    private String phoneNumber;

    @NotBlank(message = "Enter your email")
    @Email(message = "Incorrect mail entry")
    private String email;

    private boolean registered;
    private boolean confirmed;

    @NotNull
    private Set<Role> roles;

    private Set<Yacht> yachts;

    @Override
    public Profile toDto(Account entity) {
        return Profile.builder()
                .id(entity.getId())
                .name(entity.getName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .registered(entity.isAccountRegistered())
                .confirmed(entity.isAccountConfirmed())
                .roles(entity.getRoles())
                .build();
    }

    @Override
    public Account toEntity(Profile dto) {
        return Account.builder()
                .id(dto.id)
                .name(dto.name)
                .lastName(dto.lastName)
                .email(dto.email)
                .accountRegistered(dto.registered)
                .accountConfirmed(dto.confirmed)
                .roles(dto.roles)
                .yachts(dto.yachts)
                .build();
    }
}
