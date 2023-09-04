package com.yachtrent.domain.entities;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;


@Entity
@Table(schema = "main", name = "account_table")
@Data
public class Account implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "login", columnDefinition = "VARCHAR(25) NOT NULL")
    private String login;

    @Column(name = "email", columnDefinition = "VARCHAR(50) NOT NULL")
    private String email;

    @Column(name = "password", columnDefinition = "VARCHAR(300) NOT NULL")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            schema = "main",
            name = "account_role_table",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
