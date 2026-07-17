package com.alexander.openbanking_api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    // primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // unique account number
    @Column(nullable = false, unique =true)
    private String accountNumber;

    // display name of account
    @Column(nullable = false)
    private String accountName;

    // current balance
    @Column(nullable = false)
    private BigDecimal balance;

    // account type
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;

    // owner of this account
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // account creation date
    private LocalDateTime createdAt;

}