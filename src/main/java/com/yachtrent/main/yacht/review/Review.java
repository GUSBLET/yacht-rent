package com.yachtrent.main.yacht.review;

import com.yachtrent.main.account.Account;
import com.yachtrent.main.yacht.Yacht;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "main", name = "review_table")
@ToString
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number_of_stars", columnDefinition = "smallint")
    private short numberOfStars;

    @Column(name = "description", columnDefinition = "Varchar(1000) not null")
    private String description;

    @ManyToOne
    @JoinColumn(name = "yacht_id")
    private Yacht yacht;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
