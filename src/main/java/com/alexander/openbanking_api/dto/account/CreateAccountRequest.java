package com.alexander.openbanking_api.dto.account;

import com.alexander.openbanking_api.entity.AccountType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

// request body used when opening a new account
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAccountRequest {

    // account type selected by customer
    @NotNull(message = "Account type is required")
    private AccountType accountType;

}