package com.yachtrent.main.order;


import com.yachtrent.main.account.Account;
import com.yachtrent.main.yacht.Yacht;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "main", name = "order_table")
@ToString
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Float price;

    @Column(name = "status", columnDefinition = "varchar(20) not nll")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "start_of_rent")
    private LocalTime startOfRent;

    @Column(name = "finish_of_rent")
    private LocalTime finishOfRent;

    @Column(name = "number_of_persons")
    private int numberOfPersons;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "yacht_id")
    private Yacht yacht;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
