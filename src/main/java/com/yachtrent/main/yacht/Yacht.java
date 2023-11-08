package com.yachtrent.main.yacht;


import com.yachtrent.main.account.Account;
import com.yachtrent.main.harbor.Harbor;
import com.yachtrent.main.order.Order;
import com.yachtrent.main.review.Review;
import com.yachtrent.main.yacht.creator.Creator;
import com.yachtrent.main.yacht.facility.Facility;
import com.yachtrent.main.yacht.photo.YachtPhoto;
import com.yachtrent.main.yacht.type.YachtType;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "main", name = "yacht_table")
@ToString
public class Yacht {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", columnDefinition = "VARCHAR(30) NOT NULL UNIQUE")
    private String  name;

    @Column(name = "age", columnDefinition = "smallint")
    private short  age;

    @Column(name = "crew", columnDefinition = "smallint")
    private short  crew;

    @Column(name = "price_per_hour", columnDefinition = "real")
    private float pricePerHour;

    @Column(name = "length", columnDefinition = "real")
    private float  length;

    @Column(name = "width", columnDefinition = "real")
    private float  width;

    @Column(name = "capacity", columnDefinition = "smallint")
    private short  capacity;

    @Column(name = "description", columnDefinition = "varchar(1000)")
        private String  description;

    @OneToMany(mappedBy = "yacht", cascade = CascadeType.REMOVE)
    private List<YachtPhoto> photos;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JoinTable(
            schema = "main",
            name = "yacht_harbor_table",
            joinColumns = @JoinColumn(name = "yacht_id"),
            inverseJoinColumns = @JoinColumn(name = "harbor_id")
    )
    private Set<Harbor> harbors = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JoinTable(
            schema = "main",
            name = "yacht_order_table",
            joinColumns = @JoinColumn(name = "yacht_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id")
    )
    private Set<Order> orders = new HashSet<>();

    @OneToMany(mappedBy = "yacht", cascade = CascadeType.REMOVE)
    private Set<Facility> facilities = new HashSet<>();

    @OneToMany(mappedBy = "yacht", cascade = CascadeType.REMOVE)
    private Set<Review> reviews = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "type_yacht_id")
    private YachtType yachtType;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private Creator creator;
}
