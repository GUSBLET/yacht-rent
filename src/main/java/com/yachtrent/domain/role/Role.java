package com.yachtrent.domain.role;

import com.yachtrent.domain.account.Account;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
@ToString(exclude = {"account"})
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String role;

    @ManyToMany(mappedBy = "roles", cascade = CascadeType.PERSIST)
    private List<Account> account;

    @Override
    public String getAuthority() {
            return role;
    }
}
