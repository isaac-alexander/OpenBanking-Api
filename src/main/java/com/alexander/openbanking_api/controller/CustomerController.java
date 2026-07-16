package com.alexander.openbanking_api.controller;

import com.alexander.openbanking_api.dto.customer.CustomerResponse;
import com.alexander.openbanking_api.dto.customer.UpdateCustomerRequest;
import com.alexander.openbanking_api.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController {

    // inject service
    private final CustomerService customerService;

    // return logged-in customer
    @GetMapping("/me")
    public CustomerResponse getCurrentCustomer() {

        return customerService.getCurrentCustomer();

    }

    // return one customer
    @GetMapping("/{id}")
    public CustomerResponse getCustomerById(
            @PathVariable Long id) {

        return customerService.getCustomerById(id);

    }

    // return all customers
    @GetMapping
    public List<CustomerResponse> getAllCustomers() {

        return customerService.getAllCustomers();

    }

    // update logged-in customer
    @PatchMapping
    public CustomerResponse updateCustomer(

            @Valid

            @RequestBody

            UpdateCustomerRequest request) {

        return customerService.updateCurrentCustomer(request);

    }

    // delete customer
    @DeleteMapping("/{id}")
    public void deleteCustomer(

            @PathVariable Long id) {

        customerService.deleteCustomer(id);

    }

}