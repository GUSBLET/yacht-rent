package com.yachtrent.main.account.token;

import com.yachtrent.main.account.Account;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "main", name = "token_table")
@ToString
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @Column(name = "time_of_creation", updatable = false)
    private LocalDateTime timeOfCreation;

    @Column(name = "lifetime", updatable = false)
    private LocalDateTime lifetime;

    @Column(name = "confirmation_at")
    private LocalDateTime confirmationAt;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
