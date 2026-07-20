package com.alexander.openbanking_api.service.impl;

import com.alexander.openbanking_api.dto.transfer.CreateTransferRequest;
import com.alexander.openbanking_api.dto.transfer.TransferResponse;
import com.alexander.openbanking_api.entity.Account;
import com.alexander.openbanking_api.entity.Customer;
import com.alexander.openbanking_api.entity.Transfer;
import com.alexander.openbanking_api.entity.TransferStatus;
import com.alexander.openbanking_api.mapper.TransferMapper;
import com.alexander.openbanking_api.repository.AccountRepository;
import com.alexander.openbanking_api.repository.CustomerRepository;
import com.alexander.openbanking_api.repository.TransferRepository;
import com.alexander.openbanking_api.service.TransferService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl
        implements TransferService {

    private final TransferRepository transferRepository;

    private final AccountRepository accountRepository;

    private final CustomerRepository customerRepository;

    private final TransferMapper transferMapper;

    @Override
    @Transactional
    public TransferResponse transfer(
            Long customerId,
            CreateTransferRequest request) {

        Customer customer = getAuthorizedCustomer(customerId);

        Account sourceAccount = accountRepository
                .findByAccountNumber(
                        request.getSourceAccountNumber())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Source account not found"));

        Account destinationAccount = accountRepository
                .findByAccountNumber(
                        request.getDestinationAccountNumber())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Destination account not found"));

        // ensure customer owns source account
        if (!sourceAccount.getCustomer().getId()
                .equals(customer.getId())) {

            throw new AccessDeniedException(
                    "You cannot transfer from this account");

        }

        // cannot transfer to same account
        if (sourceAccount.getId()
                .equals(destinationAccount.getId())) {

            throw new RuntimeException(
                    "Cannot transfer to same account");

        }

        // sufficient balance check
        if (sourceAccount.getBalance()
                .compareTo(request.getAmount()) < 0) {

            throw new RuntimeException(
                    "Insufficient balance");

        }

        // debit sender
        sourceAccount.setBalance(
                sourceAccount.getBalance()
                        .subtract(request.getAmount()));

        // credit receiver
        destinationAccount.setBalance(
                destinationAccount.getBalance()
                        .add(request.getAmount()));

        accountRepository.save(sourceAccount);

        accountRepository.save(destinationAccount);

        Transfer transfer = Transfer.builder()

                .sourceAccount(sourceAccount)

                .destinationAccount(destinationAccount)

                .amount(request.getAmount())

                .narration(request.getNarration())

                .status(TransferStatus.SUCCESS)

                .createdAt(LocalDateTime.now())

                .build();

        transferRepository.save(transfer);

        return transferMapper.toResponse(transfer);

    }

    @Override
    public List<TransferResponse> getTransfers(
            Long customerId) {

        Customer customer = getAuthorizedCustomer(customerId);

        // find every account that belongs to the logged-in customer
        return accountRepository.findByCustomer(customer)

                // convert the list of accounts into a stream
                // streams let us process collections one item at a time
                .stream()

                // for each account, find every transfer made from that account
                // each account returns its own list of transfers
                // flatMap combines all those lists into one big stream
                .flatMap(account ->

                        transferRepository

                                // get transfers sent from this account
                                .findBySourceAccount(account)

                                // convert transfer list into a stream
                                .stream())

                // convert every Transfer entity into TransferResponse DTO
                .map(transferMapper::toResponse)

                // convert the stream back into a List
                .toList();

    }

    // verify authenticated customer
    private Customer getAuthorizedCustomer(Long customerId) {

        Customer customer = customerRepository.findById(customerId)

                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        if (!customer.getEmail().equals(authentication.getName())) {

            throw new AccessDeniedException("Unauthorized access");

        }

        return customer;

    }

}