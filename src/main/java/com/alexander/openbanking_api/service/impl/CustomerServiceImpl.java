package com.alexander.openbanking_api.service.impl;

import com.alexander.openbanking_api.dto.customer.CustomerResponse;
import com.alexander.openbanking_api.dto.customer.UpdateCustomerRequest;
import com.alexander.openbanking_api.entity.Customer;
import com.alexander.openbanking_api.mapper.CustomerMapper;
import com.alexander.openbanking_api.repository.CustomerRepository;
import com.alexander.openbanking_api.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    // repository
    private final CustomerRepository customerRepository;

    // mapper
    private final CustomerMapper customerMapper;

    // return one customer
    @Override
    public CustomerResponse getCustomerById(Long id) {

        Customer customer = findCustomer(id);

        authorize(customer);

        return customerMapper.toResponse(customer);

    }

    // update customer
    @Override
    public CustomerResponse updateCustomer(
            Long id,
            UpdateCustomerRequest request) {

        Customer customer = findCustomer(id);

        authorize(customer);

        if (request.getFirstName() != null &&
                !request.getFirstName().isBlank()) {

            customer.setFirstName(request.getFirstName());

        }

        if (request.getLastName() != null &&
                !request.getLastName().isBlank()) {

            customer.setLastName(request.getLastName());

        }

        if (request.getEmail() != null &&
                !request.getEmail().isBlank() &&
                !request.getEmail().equals(customer.getEmail())) {

            if (customerRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("Email already exists");
            }

            customer.setEmail(request.getEmail());

        }

        if (request.getPhoneNumber() != null &&
                !request.getPhoneNumber().isBlank()) {

            customer.setPhoneNumber(request.getPhoneNumber());

        }

        if (request.getAddress() != null &&
                !request.getAddress().isBlank()) {

            customer.setAddress(request.getAddress());

        }

        customerRepository.save(customer);

        return customerMapper.toResponse(customer);

    }

    // delete customer
    @Override
    public void deleteCustomer(Long id) {

        Customer customer = findCustomer(id);

        authorize(customer);

        customerRepository.delete(customer);

    }

    // find customer by id
    private Customer findCustomer(Long id) {

        return customerRepository.findById(id)

                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

    }

    // ensure logged-in customer owns this account
    private void authorize(Customer customer) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        if (!customer.getEmail().equals(email)) {

            throw new AccessDeniedException(
                    "You are not authorized to access this customer");

        }

    }

}