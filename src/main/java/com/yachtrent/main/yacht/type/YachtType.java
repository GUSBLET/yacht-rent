package com.yachtrent.main.yacht.type;

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
@Table(schema = "main", name = "yacht_type_table")
@ToString
public class YachtType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type", columnDefinition = "Varchar(30) unique ")
    private String type;

    @ManyToMany(mappedBy = "yachtTypes", cascade = CascadeType.PERSIST)
    private Set<Yacht> yachts = new HashSet<>();
}
