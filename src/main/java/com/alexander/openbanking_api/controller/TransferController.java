package com.alexander.openbanking_api.controller;

import com.alexander.openbanking_api.dto.response.ApiResponse;
import com.alexander.openbanking_api.dto.response.ApiResponseBuilder;
import com.alexander.openbanking_api.dto.transfer.CreateTransferRequest;
import com.alexander.openbanking_api.dto.transfer.TransferResponse;
import com.alexander.openbanking_api.service.TransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// handles transfer endpoints
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers/{customerId}")
public class TransferController {

    // transfer service
    private final TransferService transferService;

    // builds standard api responses
    private final ApiResponseBuilder responseBuilder;

    // perform a transfer from one account
    @PostMapping("/accounts/{accountId}/transfers")
    public ResponseEntity<ApiResponse<TransferResponse>> transfer(

            @PathVariable Long customerId,
            @PathVariable Long accountId,
            @Valid
            @RequestBody
            CreateTransferRequest request) {

        // perform transfer
        TransferResponse response = transferService.transfer(
                customerId, accountId, request);

        // return wrapped response
        return ResponseEntity.status(HttpStatus.CREATED)

                .body(
                        responseBuilder.success(
                                "Transfer completed successfully",
                                response)
                );
    }

    // return every transfer made by the customer
    @GetMapping("/transfers")
    public ResponseEntity<ApiResponse<List<TransferResponse>>> getTransfers(

            @PathVariable Long customerId) {

        // fetch transfers
        List<TransferResponse> response =
                transferService.getTransfers(customerId);

        // wrap response
        return ResponseEntity.ok(
                responseBuilder.success(
                        "Transfers retrieved successfully",
                        response)
        );

    }

}