package com.yachtrent.domain.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Table(schema = "main", name = "timetable_table")
@Entity
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
