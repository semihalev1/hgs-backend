package com.hgs.backend.service;

import com.hgs.backend.dto.TransactionRequest;
import com.hgs.backend.exception.InsufficientBalanceException;
import com.hgs.backend.model.Transaction;
import com.hgs.backend.model.Vehicle;
import com.hgs.backend.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
@RequiredArgsConstructor
@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final VehicleService vehicleService;

    @Transactional
    public Transaction createTransaction(TransactionRequest request) {
        Vehicle vehicle = vehicleService.getVehicleByPlate(request.getPlate());

        if (vehicle.getBalance() < request.getFee()) {
            throw new InsufficientBalanceException("Yetersiz Bakiye! Mevcut bakiye: "+vehicle.getBalance());
        }

        vehicle.setBalance(vehicle.getBalance() - request.getFee());
        vehicleService.addVehicle(vehicle);

        Transaction transaction = new Transaction();
        transaction.setStationName(request.getStationName());
        transaction.setFee(request.getFee());
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setVehicle(vehicle);

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionsByPlate(String plate) {
        return transactionRepository.findByVehiclePlate(plate);
    }

}