package com.alexander.openbanking_api.dto.transfer;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

// request body for transferring money
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTransferRequest {

    // destination account number
    @NotBlank(message = "Destination account number is required")
    private String destinationAccountNumber;

    // transfer amount
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "1.00", message = "Amount must be greater than zero")
    private BigDecimal amount;

    // transfer narration
    @NotBlank(message = "Narration is required")
    private String narration;

}