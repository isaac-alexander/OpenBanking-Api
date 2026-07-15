package com.alexander.openbanking_api.entity;

import jakarta.persistence.*;
import lombok.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer implements UserDetails {

    // primary key for the customer table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // customer's first name
    @Column(nullable = false)
    private String firstName;

    // customer's last name
    @Column(nullable = false)
    private String lastName;

    // email is unique because customers login with their email
    @Column(nullable = false, unique = true)
    private String email;

    // encrypted password (never store plain text passwords)
    @Column(nullable = false)
    private String password;

    // customer role (CUSTOMER or ADMIN)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // date and time the customer registered
    private LocalDateTime createdAt;

    // Spring Security Methods

    // returns the customer's role
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(new SimpleGrantedAuthority(role.name()));

    }

    // tells spring security to use email as the username
    @Override
    public String getUsername() {

        return email;

    }

    // account never expires
    @Override
    public boolean isAccountNonExpired() {

        return true;

    }

    // account is never locked
    @Override
    public boolean isAccountNonLocked() {

        return true;

    }

    // password never expires
    @Override
    public boolean isCredentialsNonExpired() {

        return true;

    }

    // account is enabled
    @Override
    public boolean isEnabled() {

        return true;

    }

}