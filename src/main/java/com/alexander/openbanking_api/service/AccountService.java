package com.alexander.openbanking_api.service;

import com.alexander.openbanking_api.dto.account.AccountResponse;
import com.alexander.openbanking_api.dto.account.CreateAccountRequest;
import com.alexander.openbanking_api.dto.account.UpdateAccountRequest;
import com.alexander.openbanking_api.dto.transfer.TransferResponse;

import java.util.List;

public interface AccountService {

    AccountResponse createAccount(Long customerId, CreateAccountRequest request);

    List<AccountResponse> getCustomerAccounts(Long customerId);

    AccountResponse getAccountById(Long customerId, Long accountId);

    AccountResponse updateAccount(Long customerId, Long accountId, UpdateAccountRequest request);

    List<TransferResponse> getAccountTransactions(Long customerId, Long accountId);

}