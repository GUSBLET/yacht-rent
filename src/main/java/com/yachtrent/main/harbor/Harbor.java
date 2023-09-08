package com.yachtrent.main.harbor;

import com.yachtrent.main.yacht.Yacht;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Enter your name")
    @Max(value = 50, message = "The name must not exceed 50 characters")
    private String  name;

    @Column(name = "address", nullable = false, unique = true)
    @Max(value = 150, message = "The address must not exceed 150 characters")
    private String  address;

    private float  longitude;
    private float  latitude;

    @ManyToMany(mappedBy = "harbors", cascade = CascadeType.PERSIST)
    private Set<Yacht> yachts = new HashSet<>();
}
