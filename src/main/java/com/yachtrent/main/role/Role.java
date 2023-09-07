package com.yachtrent.main.role;

import com.yachtrent.main.account.Account;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "main", name = "role")
@ToString(exclude = {"accountList"})
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role;

    @ManyToMany(mappedBy = "roles", cascade = CascadeType.PERSIST)
    private List<Account> accountList;

    @Override
    public String getAuthority() {
            return role;
    }
}
