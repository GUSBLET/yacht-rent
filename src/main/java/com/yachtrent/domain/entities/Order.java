package com.yachtrent.domain.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "main", name = "order_table")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "custumer_name", nullable = false, length = 50)
    private String customerName;

    @Column(name = "custumer_phone_number", unique = true, nullable = false, length = 20)
    private String customerPhoneNumber;

    @Column(name = "custumer_email", nullable = false, length = 50)
    private String customerEmail;

    @Column(name = "price")
    private Float price;

    @Column(name = "order_confirmed")
    private boolean orderСonfirmed;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            schema = "main",
            name = "timetable_order_table",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "timetable_id")
    )
    private Set<Timetable> timetables = new HashSet<>();

}
