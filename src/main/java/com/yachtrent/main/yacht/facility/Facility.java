package com.yachtrent.main.yacht.facility;

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
@Table(schema = "main", name = "facility_table")
@ToString
public class Facility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", columnDefinition = "Varchar(100) not null")
    private String name;

    @Column(name = "count", columnDefinition = "smallint")
    private short count;

    @ManyToOne
    @JoinColumn(name = "yacht_id")
    private Yacht yacht;
}
