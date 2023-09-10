package com.yachtrent.main.account;


import com.yachtrent.main.order.Order;
import com.yachtrent.main.role.Role;
import com.yachtrent.main.yacht.Yacht;
import com.yachtrent.main.yacht.review.Review;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "main", name = "account_table")
@ToString(exclude = {"orders", "reviews", "yachts"})
public class Account implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Enter your email")
    @Column(name = "email", unique = true)
    @Email(message = "Incorrect mail entry")
    private String email;

    @NotBlank(message = "Enter your password")
    private String password;

    @NotBlank(message = "Enter your name")
    @Max(value = 30, message = "The name must not exceed 30 characters")
    private String name;

    @NotBlank(message = "Enter your lastName")
    @Max(value = 30, message = "The lastName must not exceed 30 characters")
    private String lastName;

    @NotBlank(message = "Enter your phone number")
    @Max(value = 10, message = "Mobile number cannot exceed more than 10 digits")
    @Pattern(regexp = "\\+380\\d{1,10}", message = "Invalid phone number format")
    private String phoneNumber;

    private byte[] avatar;
    private boolean accountRegistered;
    private boolean accountConfirmed;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            schema = "main",
            name = "role_account_table",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @OneToMany(mappedBy = "account", cascade = CascadeType.PERSIST)
    private List<Order> orders;

    @OneToMany(mappedBy = "account", cascade = CascadeType.PERSIST)
    private Set<Review> reviews = new HashSet<>();

    @OneToMany(mappedBy = "account", cascade = CascadeType.PERSIST)
    private Set<Yacht> yachts = new HashSet<>();

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
