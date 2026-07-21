package com.alexander.openbanking_api.service;

import com.alexander.openbanking_api.dto.account.AccountResponse;
import com.alexander.openbanking_api.dto.account.CreateAccountRequest;
import com.alexander.openbanking_api.dto.account.UpdateAccountRequest;
import com.alexander.openbanking_api.dto.transfer.TransferResponse;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    // open a new account
    AccountResponse createAccount(Long customerId, CreateAccountRequest request);

    // return every account owned by customer
    List<AccountResponse> getCustomerAccounts(Long customerId);

    // return one account
    AccountResponse getAccountById(Long customerId, Long accountId);

    // return current balance
    BigDecimal getAccountBalance(Long customerId, Long accountId);

    // update account information
    AccountResponse updateAccount(Long customerId, Long accountId, UpdateAccountRequest request);

    // return transfers for one account
    List<TransferResponse> getAccountTransactions(Long customerId, Long accountId);

}