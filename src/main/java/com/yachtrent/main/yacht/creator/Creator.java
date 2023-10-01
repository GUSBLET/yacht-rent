package com.yachtrent.main.yacht.creator;

import com.yachtrent.main.yacht.Yacht;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "main", name = "creator_table")
@ToString
public class Creator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name", columnDefinition = "Varchar(100) not null unique")
    private String companyName;

    @Column(name = "phone", columnDefinition = "Varchar(30) not null unique")
    private String phone;

    @Column(name = "mail", columnDefinition = "Varchar(100) not null unique")
    private String mail;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.PERSIST)
    private List<Yacht> yachts;

}
