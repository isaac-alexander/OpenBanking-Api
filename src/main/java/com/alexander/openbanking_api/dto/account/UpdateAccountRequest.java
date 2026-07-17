package com.alexander.openbanking_api.dto.account;

import lombok.*;

// request body used for updating an account
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateAccountRequest {

    // new display name
    private String accountName;

}