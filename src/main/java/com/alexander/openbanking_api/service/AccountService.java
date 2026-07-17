package com.alexander.openbanking_api.service;

import com.alexander.openbanking_api.dto.account.AccountResponse;
import com.alexander.openbanking_api.dto.account.CreateAccountRequest;

import java.util.List;

public interface AccountService {

    AccountResponse createAccount(Long customerId, CreateAccountRequest request);

    List<AccountResponse> getCustomerAccounts(Long customerId);

    AccountResponse getAccountById(Long customerId, Long accountId);

}