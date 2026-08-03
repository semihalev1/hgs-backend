package com.hgs.backend.mapper;

import com.hgs.backend.dto.transaction.TransactionResponse;
import com.hgs.backend.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public TransactionResponse convertToResponse(Transaction transaction) {
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setId(transaction.getId());
        transactionResponse.setVehiclePlate(transaction.getVehicle().getPlate());
        transactionResponse.setFee(transaction.getFee());
        transactionResponse.setTransactionDate(transaction.getTransactionDate());
        transactionResponse.setGateId(transaction.getGate().getId());
        transactionResponse.setGateCode(transaction.getGate().getCode());
        transactionResponse.setGateName(transaction.getGate().getName());
        transactionResponse.setVehicleClassCode(transaction.getVehicleClass().getCode());
        transactionResponse.setVehicleClassId(transaction.getVehicleClass().getId());
        transactionResponse.setVehicleClassName(transaction.getVehicleClass().getName());

        return transactionResponse;
    }

}
