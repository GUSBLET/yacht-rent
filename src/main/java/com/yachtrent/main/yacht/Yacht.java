package com.yachtrent.main.yacht;


import com.yachtrent.main.harbor.Harbor;
import com.yachtrent.main.yacht.photo.YachtPhoto;
import com.yachtrent.main.yacht.type.YachtType;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "yacht_table")
@ToString
public class Yacht {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", columnDefinition = "VARCHAR(30) NOT NULL UNIQUE")
    private String  name;

    @Column(name = "age", columnDefinition = "smallint")
    private String  age;

    @Column(name = "smallint", columnDefinition = "smallint")
    private String  crew;

    @Column(name = "price_per_hour", columnDefinition = "real")
    private String  price_per_hour;

    @Column(name = "length", columnDefinition = "real")
    private String  length;

    @Column(name = "width", columnDefinition = "real")
    private String  width;

    @Column(name = "captain", columnDefinition = "varchar(50) not null")
    private String  captain;

    @Column(name = "description", columnDefinition = "varchar(1000)")
    private String  description;

    @OneToMany
    private List<YachtPhoto> photos;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "yacht_type_yacht_table",
            joinColumns = @JoinColumn(name = "yacht_id"),
            inverseJoinColumns = @JoinColumn(name = "yacht_type_id")
    )
    private Set<YachtType> yachtTypes = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "yacht_harbor_table",
            joinColumns = @JoinColumn(name = "yacht_id"),
            inverseJoinColumns = @JoinColumn(name = "yacht_harbor_id")
    )
    private Set<Harbor> harbors = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "order_yacht_table",
            joinColumns = @JoinColumn(name = "yacht_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id")
    )
    private Set<Harbor> orders = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "yacht_parameter_yacht_table",
            joinColumns = @JoinColumn(name = "yacht_id"),
            inverseJoinColumns = @JoinColumn(name = "yacht_parameter_id")
    )
    private Set<Harbor> parameters = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "rent_timetable_yacht_table",
            joinColumns = @JoinColumn(name = "yacht_id"),
            inverseJoinColumns = @JoinColumn(name = "rent_timetable_id")
    )
    private Set<Harbor> rentTimetabels = new HashSet<>();
}
