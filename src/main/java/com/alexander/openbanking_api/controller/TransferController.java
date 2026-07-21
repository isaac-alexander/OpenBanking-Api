package com.alexander.openbanking_api.controller;

import com.alexander.openbanking_api.dto.transfer.CreateTransferRequest;
import com.alexander.openbanking_api.dto.transfer.TransferResponse;
import com.alexander.openbanking_api.service.TransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// handles transfer endpoints
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers/{customerId}/transfers")
public class TransferController {

    // inject transfer service
    private final TransferService transferService;

    // perform transfer
    @PostMapping("/accounts/{accountId}/transfers")
    public TransferResponse transfer(

            @PathVariable Long customerId,

            @PathVariable Long accountId,

            @Valid

            @RequestBody

            CreateTransferRequest request) {

        return transferService.transfer(

                customerId,

                accountId,

                request);

    }

    // get transfer history
    @GetMapping
    public List<TransferResponse> getTransfers(

            @PathVariable Long customerId) {

        return transferService.getTransfers(customerId);

    }

}