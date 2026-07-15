package com.alexander.openbanking_api.security;

import com.alexander.openbanking_api.entity.Customer;
import com.alexander.openbanking_api.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

// loads customer details during authentication
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    // inject customer repository
    private final CustomerRepository customerRepository;

    // load customer using email
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        return customerRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Customer not found"));

    }

}