package com.alexander.openbanking_api.mapper;

import com.alexander.openbanking_api.dto.account.AccountResponse;
import com.alexander.openbanking_api.entity.Account;
import org.springframework.stereotype.Component;

// converts entity into dto
@Component
public class AccountMapper {

    // convert account entity to response dto
    public AccountResponse toResponse(Account account) {

        return AccountResponse.builder()

                .id(account.getId())

                .accountNumber(account.getAccountNumber())

                .accountName(account.getAccountName())

                .currency(account.getCurrency())

                .balance(account.getBalance())

                .accountType(account.getAccountType())

                .status(account.getStatus())

                .customerId(account.getCustomer().getId())

                .build();

    }

}