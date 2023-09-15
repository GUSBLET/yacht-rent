package com.yachtrent.main.order;


import com.yachtrent.main.account.Account;
import com.yachtrent.main.yacht.Yacht;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


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
    private String status;

    @Column(name = "start_of_rent", columnDefinition = "date")
    private Instant startOfRent;

    @Column(name = "finish_of_rent", columnDefinition = "date")
    private Instant finishOfRent;

    @ManyToMany(mappedBy = "orders", cascade = CascadeType.PERSIST)
    private Set<Yacht> yachts = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
