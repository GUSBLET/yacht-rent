package com.yachtrent.domain.entities;

import com.yachtrent.domain.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(schema = "main",name = "account_table")
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    @Column(name = "login",
            columnDefinition = "VARCHAR(25) NOT NULL")
    public String login;
    @Column(name = "email",
            columnDefinition = "VARCHAR(MAX) NOT NULL")
    public String email;
    @Column(name = "password",
            columnDefinition = "VARCHAR(MAX) NOT NULL")
    public String password;
    @Column(name = "role",
            columnDefinition = "VARCHAR(20) NOT NULL")
    @Enumerated(EnumType.STRING)
    public Role role;
}
