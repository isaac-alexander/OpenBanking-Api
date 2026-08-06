package com.alexander.openbanking_api.controller;

import com.alexander.openbanking_api.dto.account.AccountResponse;
import com.alexander.openbanking_api.dto.account.CreateAccountRequest;
import com.alexander.openbanking_api.dto.account.UpdateAccountRequest;
import com.alexander.openbanking_api.dto.response.ApiResponse;
import com.alexander.openbanking_api.dto.response.ApiResponseBuilder;
import com.alexander.openbanking_api.dto.transfer.TransferResponse;
import com.alexander.openbanking_api.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.alexander.openbanking_api.dto.response.ApiResponse;
import com.alexander.openbanking_api.dto.response.PageResponse;
import com.alexander.openbanking_api.dto.response.ApiResponseBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers/{customerId}/accounts")
public class AccountController {

    // account service
    private final AccountService accountService;

    // response wrapper builder
    private final ApiResponseBuilder responseBuilder;

    // open account
    @PostMapping
    public ResponseEntity<ApiResponse<AccountResponse>> createAccount(

            @PathVariable Long customerId,
            @Valid
            @RequestBody
            CreateAccountRequest request) {

        AccountResponse response = accountService.createAccount(
                customerId, request);

        return ResponseEntity.status(HttpStatus.CREATED)

                .body(
                        responseBuilder.success(
                                "Account created successfully",
                                response)
                );
    }

    // get all accounts
    @GetMapping
    public ApiResponse<PageResponse<AccountResponse>> getCustomerAccounts(
            @PathVariable Long customerId,
            @RequestParam(defaultValue = "0")
            int page,
            @RequestParam(defaultValue = "10")
            int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<AccountResponse> accounts = accountService

                .getCustomerAccounts(customerId, pageable);

        return responseBuilder.successPage(

                "Accounts retrieved successfully",

                accounts);

    }

    // get one account
    @GetMapping("/{accountId}")
    public ResponseEntity<ApiResponse<AccountResponse>> getAccountById(
            @PathVariable Long customerId,
            @PathVariable Long accountId) {

        AccountResponse response = accountService.getAccountById(
                customerId, accountId);

        return ResponseEntity.ok(
                responseBuilder.success(
                        "Account retrieved successfully",
                        response)
        );

    }

    // get balance
    @GetMapping("/{accountId}/balance")
    public ResponseEntity<ApiResponse<BigDecimal>> getBalance(
            @PathVariable Long customerId,
            @PathVariable Long accountId) {

        BigDecimal response = accountService.getAccountBalance(
                customerId, accountId);

        return ResponseEntity.ok(
                responseBuilder.success(
                        "Balance retrieved successfully",
                        response)
        );
    }

    // update account
    @PatchMapping("/{accountId}")
    public ResponseEntity<ApiResponse<AccountResponse>> updateAccount(

            @PathVariable Long customerId,
            @PathVariable Long accountId,
            @Valid
            @RequestBody
            UpdateAccountRequest request) {

        AccountResponse response = accountService.updateAccount(
                customerId, accountId, request);

        return ResponseEntity.ok(
                responseBuilder.success(
                        "Account updated successfully",
                        response)
        );
    }

    // account transaction history
    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<ApiResponse<List<TransferResponse>>> getTransactions(
            @PathVariable Long customerId,
            @PathVariable Long accountId) {

        List<TransferResponse> response = accountService.getAccountTransactions(
                customerId, accountId);

        return ResponseEntity.ok(
                responseBuilder.success(
                        "Transactions retrieved successfully",
                        response
                )
        );
    }

}