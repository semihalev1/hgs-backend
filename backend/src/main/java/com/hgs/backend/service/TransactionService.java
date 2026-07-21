package com.hgs.backend.service;

import com.hgs.backend.dto.TransactionRequest;
import com.hgs.backend.dto.TransactionResponse;
import com.hgs.backend.exception.InsufficientBalanceException;
import com.hgs.backend.model.Transaction;
import com.hgs.backend.model.Vehicle;
import com.hgs.backend.repository.TransactionRepository;
import com.hgs.backend.util.TransactionHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final VehicleService vehicleService;
    private final TransactionHelper transactionHelper;

    @Transactional
    public TransactionResponse createTransaction(TransactionRequest request) {
        Vehicle vehicle = vehicleService.getVehicleEntity(request.getPlate());

        if (vehicle.getBalance() < request.getFee()) {
            throw new InsufficientBalanceException("Yetersiz Bakiye! Mevcut bakiye: "+vehicle.getBalance());
        }

        vehicle.setBalance(vehicle.getBalance() - request.getFee());

        Transaction transaction = new Transaction();
        transaction.setStationName(request.getStationName());
        transaction.setFee(request.getFee());
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setVehicle(vehicle);

        Transaction savedTransaction = transactionRepository.save(transaction);
        return transactionHelper.convertToResponse(savedTransaction);
    }

    public List<TransactionResponse> getTransactionsByPlate(String plate) {
        return transactionRepository.findByVehiclePlate(plate)
                .stream()
                .map(transactionHelper::convertToResponse)
                .collect(Collectors.toList());

    }

}