package com.alexander.openbanking_api.service.impl;

import com.alexander.openbanking_api.dto.account.AccountResponse;
import com.alexander.openbanking_api.dto.account.CreateAccountRequest;
import com.alexander.openbanking_api.dto.account.UpdateAccountRequest;
import com.alexander.openbanking_api.entity.Account;
import com.alexander.openbanking_api.entity.AccountStatus;
import com.alexander.openbanking_api.entity.Currency;
import com.alexander.openbanking_api.entity.Customer;
import com.alexander.openbanking_api.mapper.AccountMapper;
import com.alexander.openbanking_api.mapper.TransferMapper;
import com.alexander.openbanking_api.repository.AccountRepository;
import com.alexander.openbanking_api.repository.CustomerRepository;
import com.alexander.openbanking_api.repository.TransferRepository;
import com.alexander.openbanking_api.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.alexander.openbanking_api.dto.transfer.TransferResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    // account repository
    private final AccountRepository accountRepository;

    // customer repository
    private final CustomerRepository customerRepository;

    // account mapper
    private final AccountMapper accountMapper;

    private final TransferRepository transferRepository;

    private final TransferMapper transferMapper;

    @Override
    public AccountResponse createAccount(
            Long customerId,
            CreateAccountRequest request) {

        Customer customer = getAuthorizedCustomer(customerId);

        Account account = Account.builder()

                .accountNumber(generateAccountNumber())

                .accountName(request.getAccountName())

                .accountType(request.getAccountType())

                // every new account gets a balance of 1000
                .balance(BigDecimal.valueOf(1000))

                .currency(Currency.NGN)

                .status(AccountStatus.ACTIVE)

                .createdAt(LocalDateTime.now())

                .customer(customer)

                .build();

        accountRepository.save(account);

        return accountMapper.toResponse(account);

    }

    @Override
    public List<AccountResponse> getCustomerAccounts(Long customerId) {

        Customer customer = getAuthorizedCustomer(customerId);

        return accountRepository.findByCustomer(customer)

                .stream()

                .map(accountMapper::toResponse)

                .toList();

    }

    @Override
    public AccountResponse getAccountById(Long customerId, Long accountId) {

        // verify ownership and return the account
        Account account = getAuthorizedAccount(customerId, accountId);

        // convert entity into response dto
        return accountMapper.toResponse(account);

    }

    // return current account balance
    @Override
    public BigDecimal getAccountBalance(Long customerId, Long accountId) {

        // ensure the account belongs to the logged-in customer
        Account account = getAuthorizedAccount(customerId, accountId);

        // return only the account balance
        return account.getBalance();

    }

    // update account information
    @Override
    public AccountResponse updateAccount(Long customerId, Long accountId, UpdateAccountRequest request) {

        // verify ownership before making changes
        Account account = getAuthorizedAccount(customerId, accountId);

        // update account name only if a value was supplied
        if (request.getAccountName() != null &&
                !request.getAccountName().isBlank()) {
            account.setAccountName(request.getAccountName());
        }

        // save changes to database
        accountRepository.save(account);

        // convert entity into response dto
        return accountMapper.toResponse(account);

    }

    // return every transfer made from this account
    @Override
    public List<TransferResponse> getAccountTransactions(Long customerId, Long accountId) {

        // verify customer owns the account
        Account account = getAuthorizedAccount(customerId, accountId);

        // find every transfer where this account
        // is the source account
        return transferRepository

                .findBySourceAccount(account)

                // convert every transfer entity
                // into a response dto
                .stream()

                .map(transferMapper::toResponse)

                // return the results as a list
                .toList();

    }

    // verify logged-in customer
    private Customer getAuthorizedCustomer(Long customerId) {

        Customer customer = customerRepository.findById(customerId)

                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (!customer.getEmail().equals(authentication.getName())) {

            throw new AccessDeniedException(
                    "Unauthorized access");

        }

        return customer;

    }

    // verify customer owns the requested account
    private Account getAuthorizedAccount(Long customerId, Long accountId) {

        // first verify the logged-in customer
        Customer customer = getAuthorizedCustomer(customerId);

        // find the requested account
        Account account = accountRepository
                .findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // ensure the account belongs to
        // the authenticated customer
        if (!account.getCustomer()
                .getId()
                .equals(customer.getId())) {
            throw new AccessDeniedException("Unauthorized access");

        }

        // return the authorized account
        return account;

    }

    // generate unique 10-digit account number
    // first two digits are always 00
    // generate unique account number
    private String generateAccountNumber() {

        Random random = new Random();

        String accountNumber = "00";

        // generate remaining 8 digits
        for (int i = 0; i < 8; i++) {

            accountNumber += random.nextInt(10);

        }

        // if generated number already exists,
        // generate another one
        if (accountRepository.existsByAccountNumber(accountNumber)) {

            return generateAccountNumber();

        }

        return accountNumber;

    }

}