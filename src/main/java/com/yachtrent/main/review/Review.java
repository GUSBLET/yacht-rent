package com.yachtrent.main.review;

import com.yachtrent.main.account.Account;
import com.yachtrent.main.yacht.Yacht;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

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

    @Column(name = "review_rating", columnDefinition = "varchar(20) not null")
    private ReviewRating reviewRating;

    @Column(name = "description", columnDefinition = "Varchar(1000) not null")
    private String description;

    @Column(name = "title", columnDefinition = "Varchar(100) not null")
    private String title;

    @Column(name = "posting_date", columnDefinition = "date")
    private Date postingDate;

    @ManyToOne
    @JoinColumn(name = "yacht_id")
    private Yacht yacht;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
