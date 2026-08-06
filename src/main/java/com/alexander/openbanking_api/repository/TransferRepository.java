package com.alexander.openbanking_api.repository;

import com.alexander.openbanking_api.entity.Account;
import com.alexander.openbanking_api.entity.Transfer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// repository for transfer table
public interface TransferRepository
        extends JpaRepository<Transfer, Long> {

    // find transfers made from an account
    List<Transfer> findBySourceAccount(Account account);

    // find transfers received by an account
    List<Transfer> findByDestinationAccount(Account account);

    // return every transfer for one customer
    // includes money sent and money received
    @Query(
            value = """
                    SELECT t.*
                    FROM transfer t
                    INNER JOIN account a
                        ON a.id = t.source_account_id
                    WHERE a.customer_id = :customerId

                    UNION

                    SELECT t.*
                    FROM transfer t
                    INNER JOIN account a
                        ON a.id = t.destination_account_id
                    WHERE a.customer_id = :customerId

                    ORDER BY created_at DESC
                    """,
            countQuery = """
                    SELECT COUNT(*)
                    FROM transfer t
                    WHERE t.source_account_id IN
                    (
                        SELECT id
                        FROM account
                        WHERE customer_id = :customerId
                    )
                    OR t.destination_account_id IN
                    (
                        SELECT id
                        FROM account
                        WHERE customer_id = :customerId
                    )
                    """,
            nativeQuery = true
    )
    Page<Transfer> findTransfersByCustomerId(

            @Param("customerId") Long customerId,

            Pageable pageable);

}