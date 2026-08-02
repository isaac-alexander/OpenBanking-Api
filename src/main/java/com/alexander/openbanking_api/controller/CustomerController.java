package com.alexander.openbanking_api.controller;

import com.alexander.openbanking_api.dto.customer.CustomerResponse;
import com.alexander.openbanking_api.dto.customer.UpdateCustomerRequest;
import com.alexander.openbanking_api.dto.response.ApiResponse;
import com.alexander.openbanking_api.dto.response.ApiResponseBuilder;
import com.alexander.openbanking_api.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController {

    // customer service
    private final CustomerService customerService;

    // builds standard api responses
    private final ApiResponseBuilder responseBuilder;

    // get customer by id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponse>> getCustomerById(
            @PathVariable Long id) {

        CustomerResponse response = customerService.getCustomerById(id);

        return ResponseEntity.ok(
                responseBuilder.success(
                        "Customer retrieved successfully",
                        response)
        );
    }

    // update customer
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponse>> updateCustomer(
            @PathVariable Long id,
            @Valid
            @RequestBody
            UpdateCustomerRequest request) {
        CustomerResponse response = customerService.updateCustomer(
                id,
                request);

        return ResponseEntity.ok(
                responseBuilder.success(
                        "Customer updated successfully",
                        response
                )
        );

    }

    // delete customer
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCustomer(

            @PathVariable Long id) {
        customerService.deleteCustomer(id);

        return ResponseEntity.ok(
                responseBuilder.success(
                        "Customer deleted successfully",
                        "Customer deleted successfully"
                )
        );

    }

}