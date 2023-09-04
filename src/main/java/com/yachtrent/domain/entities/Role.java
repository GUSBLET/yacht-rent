package com.yachtrent.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "main" ,name = "role_table")
@ToString(exclude = {"userList"})
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String role;

    @ManyToMany(mappedBy = "roles", cascade = CascadeType.PERSIST)
    private List<Account> userList;
    @Override
    public String getAuthority() {
            return role;
    }
}
