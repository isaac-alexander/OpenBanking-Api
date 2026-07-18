package com.alexander.openbanking_api.dto.account;

import com.alexander.openbanking_api.entity.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

// request body used when opening a new account
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAccountRequest {

    @NotBlank(message = "Account name is required")
    private String accountName;

    // account type selected by customer
    @NotNull(message = "Account type is required")
    private AccountType accountType;

}