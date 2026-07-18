package com.alexander.openbanking_api.service.impl;

import com.alexander.openbanking_api.dto.account.AccountResponse;
import com.alexander.openbanking_api.dto.account.CreateAccountRequest;
import com.alexander.openbanking_api.entity.Account;
import com.alexander.openbanking_api.entity.AccountStatus;
import com.alexander.openbanking_api.entity.Currency;
import com.alexander.openbanking_api.entity.Customer;
import com.alexander.openbanking_api.mapper.AccountMapper;
import com.alexander.openbanking_api.repository.AccountRepository;
import com.alexander.openbanking_api.repository.CustomerRepository;
import com.alexander.openbanking_api.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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

    @Override
    public AccountResponse createAccount(
            Long customerId,
            CreateAccountRequest request) {

        Customer customer = getAuthorizedCustomer(customerId);

        Account account = Account.builder()

                .accountNumber(generateAccountNumber())

                .accountName(request.getAccountName())

                .accountType(request.getAccountType())

                .balance(BigDecimal.ZERO)

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
    public AccountResponse getAccountById(
            Long customerId,
            Long accountId) {

        Customer customer = getAuthorizedCustomer(customerId);

        Account account = accountRepository.findById(accountId)

                .orElseThrow(() ->
                        new RuntimeException("Account not found"));

        // ensure customer owns the account
        if (!account.getCustomer().getId().equals(customer.getId())) {

            throw new AccessDeniedException(
                    "You are not authorized to access this account");

        }

        return accountMapper.toResponse(account);

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