package com.yachtrent.main.rent.time;


import com.yachtrent.main.order.Order;
import com.yachtrent.main.yacht.Yacht;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "main", name = "rent_timetable_table")
@ToString
public class RentTimetable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_of_rent", columnDefinition = "date not null")
    private Date startOfRent;

    @Column(name = "finish_of_rent", columnDefinition = "date not null")
    private Date finishOfRent;

    @ManyToMany(mappedBy = "rentTimetables", cascade = CascadeType.PERSIST)
    private Set<Order> orders = new HashSet<>();

    @ManyToMany(mappedBy = "rentTimetabels", cascade = CascadeType.PERSIST)
    private Set<Yacht> yachts = new HashSet<>();

}
