package com.alexander.openbanking_api.repository;

import com.alexander.openbanking_api.entity.Account;
import com.alexander.openbanking_api.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// repository for transfer table
public interface TransferRepository
        extends JpaRepository<Transfer, Long> {

    // find transfers made from an account
    List<Transfer> findBySourceAccount(Account account);

    // find transfers received by an account
    List<Transfer> findByDestinationAccount(Account account);


}