package com.hgs.backend.util;

import com.hgs.backend.dto.TransactionResponse;
import com.hgs.backend.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionHelper {

    public TransactionResponse convertToResponse(Transaction transaction) {
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setId(transaction.getId());
        transactionResponse.setStationName(transaction.getStationName());
        transactionResponse.setFee(transaction.getFee());
        transactionResponse.setTransactionDate(transaction.getTransactionDate());

        if(transaction.getVehicle() != null) {
            transactionResponse.setVehiclePlate(transaction.getVehicle().getPlate());
        }
        return transactionResponse;
    }

}
