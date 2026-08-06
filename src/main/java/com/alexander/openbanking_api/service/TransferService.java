package com.alexander.openbanking_api.service;

import com.alexander.openbanking_api.dto.transfer.CreateTransferRequest;
import com.alexander.openbanking_api.dto.transfer.TransferResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransferService {

    // perform transfer
    TransferResponse transfer(Long customerId, Long accountId,CreateTransferRequest request);

    // transfer history
    Page<TransferResponse> getTransfers(Long customerId, Pageable pageable);
}