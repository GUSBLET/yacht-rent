package com.yachtrent.main.harbor;

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
@Table(schema = "main", name = "harbor_table")
@ToString
public class Harbor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", columnDefinition = "VARCHAR(50) NOT NULL UNIQUE")
    private String  name;

    @Column(name = "address", columnDefinition = "VARCHAR(150) NOT NULL")
    private String  address;

    @Column(name = "latitude", columnDefinition = "real")
    private float  latitude;

    @Column(name = "longitude", columnDefinition = "real")
    private float  longitude;

    @ManyToMany(mappedBy = "harbors", cascade = CascadeType.PERSIST)
    private Set<Yacht> yachts = new HashSet<>();
}
