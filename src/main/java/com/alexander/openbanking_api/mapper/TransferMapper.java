package com.alexander.openbanking_api.mapper;

import com.alexander.openbanking_api.dto.transfer.TransferResponse;
import com.alexander.openbanking_api.entity.Transfer;
import org.springframework.stereotype.Component;

// converts Transfer entity into TransferResponse
@Component
public class TransferMapper {

    public TransferResponse toResponse(Transfer transfer) {

        return TransferResponse.builder()

                .id(transfer.getId())

                .sourceAccountNumber(
                        transfer.getSourceAccount().getAccountNumber())

                .destinationAccountNumber(
                        transfer.getDestinationAccount().getAccountNumber())

                .amount(transfer.getAmount())

                .narration(transfer.getNarration())

                .status(transfer.getStatus())

                .createdAt(transfer.getCreatedAt())

                .build();

    }

}