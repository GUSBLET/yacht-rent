package com.yachtrent.main.order;


import com.yachtrent.main.rent.time.RentTimetable;
import com.yachtrent.main.yacht.Yacht;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "main", name = "order_table")
@ToString
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "price", columnDefinition = "real")
    private Float price;

    @Column(name = "order_confirmed", columnDefinition = "boolean")
    private boolean orderСonfirmed;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "rent-timetable_order_table",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "timetable_id")
    )
    private Set<RentTimetable> rentTimetables = new HashSet<>();

    @ManyToMany(mappedBy = "orders", cascade = CascadeType.PERSIST)
    private Set<Yacht> yachts = new HashSet<>();

}
