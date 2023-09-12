package com.yachtrent.main.order;


import com.yachtrent.main.account.Account;
import com.yachtrent.main.yacht.Yacht;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "order_confirmed", columnDefinition = "boolean")
    private boolean confirmedOrder;

    @Column(name = "start_of_rent", columnDefinition = "date")
    private Date startOfRent;

    @Column(name = "finish_of_rent", columnDefinition = "date")
    private Date finishOfRent;

    @ManyToMany(mappedBy = "orders", cascade = CascadeType.PERSIST)
    private Set<Yacht> yachts = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
