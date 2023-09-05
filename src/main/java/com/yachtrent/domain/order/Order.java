package com.yachtrent.domain.order;


import com.yachtrent.domain.time.Timetable;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"order\"")
@ToString
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_name", nullable = false, length = 50)
    private String customerName;

    @Column(name = "customer_phone_number", unique = true, nullable = false, length = 20)
    private String customerPhoneNumber;

    @Column(name = "customer_email", nullable = false, length = 50)
    private String customerEmail;

    @Column(name = "price")
    private Float price;

    @Column(name = "order_confirmed")
    private boolean orderСonfirmed;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "timetable_order",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "timetable_id")
    )
    private Set<Timetable> timetables = new HashSet<>();

}
