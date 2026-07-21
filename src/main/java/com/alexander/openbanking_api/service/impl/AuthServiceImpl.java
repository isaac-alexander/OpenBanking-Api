package com.alexander.openbanking_api.service.impl;

import com.alexander.openbanking_api.dto.auth.AuthResponse;
import com.alexander.openbanking_api.dto.auth.LoginRequest;
import com.alexander.openbanking_api.dto.auth.RegisterCustomerRequest;
import com.alexander.openbanking_api.entity.Customer;
import com.alexander.openbanking_api.entity.Role;
import com.alexander.openbanking_api.repository.CustomerRepository;
import com.alexander.openbanking_api.security.JwtService;
import com.alexander.openbanking_api.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    // repository for database operations
    private final CustomerRepository customerRepository;

    // encrypt passwords
    private final PasswordEncoder passwordEncoder;

    // generate jwt tokens
    private final JwtService jwtService;

    // authenticate login credentials
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(RegisterCustomerRequest request) {

        // check if email already exists
        if (customerRepository.existsByEmail(request.getEmail())) {

            throw new RuntimeException("Email already exists");

        }

        // create customer object
        Customer customer = Customer.builder()

                .firstName(request.getFirstName())

                .lastName(request.getLastName())

                .email(request.getEmail())

                .phoneNumber(request.getPhoneNumber())

                .address(request.getAddress())

                .password(passwordEncoder.encode(request.getPassword()))

                .role(Role.CUSTOMER)

                .createdAt(LocalDateTime.now())

                .updatedAt(LocalDateTime.now())

                .build();

        // save customer
        Customer savedCustomer = customerRepository.save(customer);

        // generate jwt token
        String token = jwtService.generateToken(customer);

        // return authentication response
        return AuthResponse.builder()

                // generated jwt
                .token(token)

                // logged in customer id
                .customerId(savedCustomer.getId())

                // customer email
                .email(customer.getEmail())

                .build();

    }

    @Override
    public AuthResponse login(LoginRequest request) {

        // authenticate user
        authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(

                        request.getEmail(),

                        request.getPassword()

                )

        );

        // find customer
        Customer customer = customerRepository.findByEmail(request.getEmail())

                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // generate token
        String token = jwtService.generateToken(customer);

        return AuthResponse.builder()

                // generated jwt
                .token(token)

                // logged in customer id
                .customerId(customer.getId())

                // customer email
                .email(customer.getEmail())

                .build();

    }

}