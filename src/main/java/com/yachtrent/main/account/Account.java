package com.yachtrent.main.account;


import com.yachtrent.main.order.Order;
import com.yachtrent.main.role.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account_table")
@ToString
public class Account implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", columnDefinition = "VARCHAR(50) NOT NULL unique")
    private String email;

    @Column(name = "password", columnDefinition = "VARCHAR(300) NOT NULL")
    private String password;

    @Column(name = "name", columnDefinition = "VARCHAR(30) NOT NULL")
    private String name;

    @Column(name = "last-name", columnDefinition = "VARCHAR(30) NOT NULL")
    private String lastName;

    @Column(name = "phone-number", columnDefinition = "VARCHAR(30) NOT NULL unique")
    private String phoneNumber;

    @Column(name = "avatar", columnDefinition = "bytea")
    private byte[] avatar;

    @Column(name = "account_registered", columnDefinition = "boolean")
    private boolean accountRegistered;

    @Column(name = "account_confirmed", columnDefinition = "boolean")
    private  boolean accountConfirmed;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "account_role",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @OneToMany
    private List<Order> orders;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
