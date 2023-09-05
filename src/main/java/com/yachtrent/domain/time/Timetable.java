package com.yachtrent.domain.time;


import com.yachtrent.domain.order.Order;
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
@Table(name = "timetable")
@ToString
public class Timetable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_of_rent", nullable = false)
    private Date startOfRent;

    @Column(name = "finish_of_rent", nullable = false)
    private Date finishOfRent;

    @ManyToMany(mappedBy = "timetables", cascade = CascadeType.PERSIST)
    private Set<Order> orders = new HashSet<>();

}
