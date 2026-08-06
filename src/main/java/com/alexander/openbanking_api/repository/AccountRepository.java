package com.alexander.openbanking_api.repository;

import com.alexander.openbanking_api.entity.Account;
import com.alexander.openbanking_api.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    // search using account number
    Optional<Account> findByAccountNumber(String accountNumber);

    // check duplicate account number
    boolean existsByAccountNumber(String accountNumber);

    // return every account belonging to one customer
    List<Account> findByCustomer(Customer customer);

    // return accounts belonging to a customer using pagination
    @Query(
            value = """
                    SELECT *
                    FROM account
                    WHERE customer_id = :customerId
                    ORDER BY id DESC
                    """,
            countQuery = """
                    SELECT COUNT(*)
                    FROM account
                    WHERE customer_id = :customerId
                    """,
            nativeQuery = true
    )
    Page<Account> findAccountsByCustomerId(

            @Param("customerId") Long customerId,

            Pageable pageable);

}