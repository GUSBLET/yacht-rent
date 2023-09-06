package com.yachtrent.main.yacht.parameter;

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
@Table(name = "yacht_parameter_table")
@ToString
public class YachtParameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", columnDefinition = "Varchar(100) unique ")
    private String name;

    @ManyToMany(mappedBy = "parameters", cascade = CascadeType.PERSIST)
    private Set<Yacht> yachts = new HashSet<>();
}
