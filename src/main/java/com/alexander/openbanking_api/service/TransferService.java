package com.alexander.openbanking_api.service;

import com.alexander.openbanking_api.dto.transfer.CreateTransferRequest;
import com.alexander.openbanking_api.dto.transfer.TransferResponse;

import java.util.List;

public interface TransferService {

    // perform transfer
    TransferResponse transfer(Long customerId, CreateTransferRequest request);

    // transfer history
    List<TransferResponse> getTransfers(Long customerId);

}