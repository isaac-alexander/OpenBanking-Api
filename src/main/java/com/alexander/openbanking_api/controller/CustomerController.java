package com.alexander.openbanking_api.controller;

import com.alexander.openbanking_api.dto.customer.CustomerResponse;
import com.alexander.openbanking_api.dto.customer.UpdateCustomerRequest;
import com.alexander.openbanking_api.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    // get customer
    @GetMapping("/{id}")
    public CustomerResponse getCustomerById(
            @PathVariable Long id) {

        return customerService.getCustomerById(id);

    }

    // update customer
    @PatchMapping("/{id}")
    public CustomerResponse updateCustomer(

            @PathVariable Long id,

            @Valid
            @RequestBody
            UpdateCustomerRequest request) {

        return customerService.updateCustomer(id, request);

    }

    // delete customer
    @DeleteMapping("/{id}")
    public void deleteCustomer(
            @PathVariable Long id) {

        customerService.deleteCustomer(id);

    }

}