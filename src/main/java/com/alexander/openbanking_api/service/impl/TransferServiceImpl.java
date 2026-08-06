package com.alexander.openbanking_api.service.impl;

import com.alexander.openbanking_api.dto.transfer.CreateTransferRequest;
import com.alexander.openbanking_api.dto.transfer.TransferResponse;
import com.alexander.openbanking_api.entity.Account;
import com.alexander.openbanking_api.entity.Customer;
import com.alexander.openbanking_api.entity.Transfer;
import com.alexander.openbanking_api.entity.TransferStatus;
import com.alexander.openbanking_api.exception.BadRequestException;
import com.alexander.openbanking_api.exception.ResourceNotFoundException;
import com.alexander.openbanking_api.mapper.TransferMapper;
import com.alexander.openbanking_api.repository.AccountRepository;
import com.alexander.openbanking_api.repository.CustomerRepository;
import com.alexander.openbanking_api.repository.TransferRepository;
import com.alexander.openbanking_api.service.TransferService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    // transfer repository
    private final TransferRepository transferRepository;

    // account repository
    private final AccountRepository accountRepository;

    // customer repository
    private final CustomerRepository customerRepository;

    // mapper
    private final TransferMapper transferMapper;

    // transfer money
    @Override
    @Transactional
    public TransferResponse transfer(
            Long customerId,
            Long accountId,
            CreateTransferRequest request) {

        // verify logged in customer
        Customer customer = getAuthorizedCustomer(customerId);

        // get sender account
        Account sourceAccount = getAuthorizedAccount(customerId, accountId);

        // find receiver account
        Account destinationAccount = accountRepository

                .findByAccountNumber(
                        request.getDestinationAccountNumber())

                // destination account number does not exist
                .orElseThrow(() ->
                        new ResourceNotFoundException("Destination account not found"));

        // customer cannot transfer to same account
        if (sourceAccount.getId()
                .equals(destinationAccount.getId())) {

            // sender and receiver cannot be the same account
            throw new BadRequestException("Cannot transfer to same account");
        }

        // ensure enough balance exists
        if (sourceAccount.getBalance()
                .compareTo(request.getAmount()) < 0) {

            // sender does not have enough money
            throw new BadRequestException("Insufficient balance");
        }

        // remove money from sender
        sourceAccount.setBalance(

                sourceAccount.getBalance()

                        .subtract(request.getAmount()));

        // add money to receiver
        destinationAccount.setBalance(

                destinationAccount.getBalance()

                        .add(request.getAmount()));

        // save sender
        accountRepository.save(sourceAccount);

        // save receiver
        accountRepository.save(destinationAccount);

        // save transfer history
        Transfer transfer = Transfer.builder()

                .sourceAccount(sourceAccount)

                .destinationAccount(destinationAccount)

                .amount(request.getAmount())

                .narration(request.getNarration())

                .status(TransferStatus.SUCCESS)

                .createdAt(LocalDateTime.now())

                .build();

        transferRepository.save(transfer);

        // return response
        return transferMapper.toResponse(transfer);

    }

    // return every transfer made by the logged-in customer
    @Override
    public Page<TransferResponse> getTransfers(

            Long customerId,

            Pageable pageable) {

        // verify logged-in customer
        Customer customer = getAuthorizedCustomer(customerId);

        // retrieve one page of transfers
        return transferRepository

                .findTransfersByCustomerId(

                        customer.getId(),

                        pageable)

                // convert entity into response dto
                .map(transferMapper::toResponse);

    }

    // return an account after verifying ownership
    private Account getAuthorizedAccount(
            Long customerId,
            Long accountId) {

        // verify logged-in customer
        Customer customer = getAuthorizedCustomer(customerId);

        // find account
        Account account = accountRepository.findById(accountId)

                .orElseThrow(() ->
                        new ResourceNotFoundException("Account not found"));

        // ensure account belongs to customer
        if (!account.getCustomer()

                .getId()

                .equals(customer.getId())) {

            throw new AccessDeniedException(
                    "Unauthorized access");

        }

        return account;

    }

    // verify logged-in customer
    private Customer getAuthorizedCustomer(
            Long customerId) {

        // find customer
        Customer customer = customerRepository.findById(customerId)

                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found"));

        // get authenticated user
        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        // compare emails
        if (!customer.getEmail()
                .equals(authentication.getName())) {

            throw new AccessDeniedException(
                    "Unauthorized access");

        }

        return customer;

    }

}