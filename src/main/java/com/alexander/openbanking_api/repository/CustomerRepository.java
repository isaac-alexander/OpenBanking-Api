package com.alexander.openbanking_api.repository;

import com.alexander.openbanking_api.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // find a customer by email
    Optional<Customer> findByEmail(String email);

    // check if an email already exists
    boolean existsByEmail(String email);

}