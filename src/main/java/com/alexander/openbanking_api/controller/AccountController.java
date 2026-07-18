package com.alexander.openbanking_api.controller;

import com.alexander.openbanking_api.dto.account.AccountResponse;
import com.alexander.openbanking_api.dto.account.CreateAccountRequest;
import com.alexander.openbanking_api.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// handles account endpoints
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers/{customerId}/accounts")
public class AccountController {

    // inject account service
    private final AccountService accountService;

    // create a new account for a customer
    @PostMapping
    public AccountResponse createAccount(

            @PathVariable Long customerId,

            @Valid

            @RequestBody

            CreateAccountRequest request) {

        return accountService.createAccount(customerId, request);

    }

    // return every account owned by a customer
    @GetMapping
    public List<AccountResponse> getCustomerAccounts(

            @PathVariable Long customerId) {

        return accountService.getCustomerAccounts(customerId);

    }

    // return one account
    @GetMapping("/{accountId}")
    public AccountResponse getAccountById(

            @PathVariable Long customerId,

            @PathVariable Long accountId) {

        return accountService.getAccountById(

                customerId,

                accountId);

    }

}