package com.alexander.openbanking_api.controller;

import com.alexander.openbanking_api.dto.response.ApiResponse;
import com.alexander.openbanking_api.dto.response.ApiResponseBuilder;
import com.alexander.openbanking_api.dto.transfer.CreateTransferRequest;
import com.alexander.openbanking_api.dto.transfer.TransferResponse;
import com.alexander.openbanking_api.service.TransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.alexander.openbanking_api.dto.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

// handles transfer endpoints
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers/{customerId}/transfers")
public class TransferController {

    // transfer service
    private final TransferService transferService;

    private final ApiResponseBuilder responseBuilder;

    // perform transfer
    @PostMapping("/accounts/{accountId}/transfers")
    public ApiResponse<TransferResponse> transfer(

            @PathVariable Long customerId,

            @PathVariable Long accountId,

            @Valid
            @RequestBody
            CreateTransferRequest request) {

        // perform transfer
        TransferResponse response = transferService.transfer(
                customerId, accountId, request);

        //return wrapped response
        return responseBuilder.success(

                "Transfer completed successfully",
                response);
    }

    // get transfer history
    @GetMapping
    public ApiResponse<PageResponse<TransferResponse>> getTransfers(

            @PathVariable Long customerId,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<TransferResponse> transfers = transferService

                .getTransfers(customerId, pageable);

        return responseBuilder.successPage(

                "Transfers retrieved successfully",

                transfers);

    }

}