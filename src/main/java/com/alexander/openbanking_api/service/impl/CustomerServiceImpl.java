package com.alexander.openbanking_api.service.impl;

import com.alexander.openbanking_api.dto.customer.CustomerResponse;
import com.alexander.openbanking_api.dto.customer.UpdateCustomerRequest;
import com.alexander.openbanking_api.entity.Customer;
import com.alexander.openbanking_api.mapper.CustomerMapper;
import com.alexander.openbanking_api.repository.CustomerRepository;
import com.alexander.openbanking_api.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    // repository
    private final CustomerRepository customerRepository;

    // mapper
    private final CustomerMapper customerMapper;

    // returns the currently logged-in customer
    @Override
    public CustomerResponse getCurrentCustomer() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        return customerMapper.toResponse(customer);

    }

    // get customer using id
    @Override
    public CustomerResponse getCustomerById(Long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        return customerMapper.toResponse(customer);

    }

    // return every customer
    @Override
    public List<CustomerResponse> getAllCustomers() {

        return customerRepository.findAll()

                .stream()

                .map(customerMapper::toResponse)

                .toList();

    }

    // update currently logged-in customer
    @Override
    public CustomerResponse updateCurrentCustomer(
            UpdateCustomerRequest request) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        Customer customer = customerRepository.findByEmail(email)

                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        customer.setFirstName(request.getFirstName());

        customer.setLastName(request.getLastName());

        customer.setPhoneNumber(request.getPhoneNumber());

        customer.setAddress(request.getAddress());

        customer.setUpdatedAt(LocalDateTime.now());

        customerRepository.save(customer);

        return customerMapper.toResponse(customer);

    }

    // delete customer
    @Override
    public void deleteCustomer(Long id) {

        Customer customer = customerRepository.findById(id)

                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        customerRepository.delete(customer);

    }

}