package com.alexander.openbanking_api.dto.transfer;

import com.alexander.openbanking_api.entity.TransferStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// response returned after transfer
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferResponse {

    // transfer id
    private Long id;

    // sender account
    private String sourceAccountNumber;

    // receiver account
    private String destinationAccountNumber;

    // transferred amount
    private BigDecimal amount;

    // transfer narration
    private String narration;

    // transfer status
    private TransferStatus status;

    // transfer time
    private LocalDateTime createdAt;

}