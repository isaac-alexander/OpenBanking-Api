package com.alexander.openbanking_api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// stores every transfer made in the system
@Entity
@Table(name = "transfers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transfer {

    // primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // account sending the money
    @ManyToOne
    @JoinColumn(name = "source_account_id")
    private Account sourceAccount;

    // account receiving the money
    @ManyToOne
    @JoinColumn(name = "destination_account_id")
    private Account destinationAccount;

    // transfer amount
    @Column(nullable = false)
    private BigDecimal amount;

    // transfer narration
    private String narration;

    // transfer status
    @Enumerated(EnumType.STRING)
    private TransferStatus status;

    // date transfer happened
    private LocalDateTime createdAt;

}