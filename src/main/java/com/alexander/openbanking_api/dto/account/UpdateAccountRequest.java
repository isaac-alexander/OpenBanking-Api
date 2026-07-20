package com.alexander.openbanking_api.dto.account;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

// request body for updating an account
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateAccountRequest {

    // new account display name
    @NotBlank(message = "Account name is required")
    private String accountName;

}