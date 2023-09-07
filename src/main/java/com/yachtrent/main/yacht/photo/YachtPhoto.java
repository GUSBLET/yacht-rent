package com.yachtrent.main.yacht.photo;

import com.yachtrent.main.yacht.Yacht;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "main", name = "yacht_photo_table")
@ToString
public class YachtPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "photo", columnDefinition = "bytea")
    private byte[] photo;
}
