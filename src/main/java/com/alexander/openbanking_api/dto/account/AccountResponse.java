package com.alexander.openbanking_api.dto.account;

import com.alexander.openbanking_api.entity.AccountStatus;
import com.alexander.openbanking_api.entity.AccountType;
import com.alexander.openbanking_api.entity.Currency;
import lombok.*;

import java.math.BigDecimal;

// response returned to clients
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponse {

    // account id
    private Long id;

    // generated account number
    private String accountNumber;

    // account display name
    private String accountName;

    // account currency
    private Currency currency;

    // current balance
    private BigDecimal balance;

    // account type
    private AccountType accountType;

    // current account status
    private AccountStatus status;

    // owner id
    private Long customerId;

}