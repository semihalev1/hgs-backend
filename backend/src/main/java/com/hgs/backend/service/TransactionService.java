package com.hgs.backend.service;

import com.hgs.backend.dto.TransactionRequest;
import com.hgs.backend.dto.TransactionResponse;
import com.hgs.backend.exception.InsufficientBalanceException;
import com.hgs.backend.model.*;
import com.hgs.backend.repository.TransactionRepository;
import com.hgs.backend.util.TransactionHelper;
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
    private final TransactionHelper transactionHelper;
    private final GateService gateService;
    private final TariffService tariffService;

    @Transactional
    public TransactionResponse createTransaction(TransactionRequest request) {
        Vehicle vehicle = vehicleService.getVehicleEntity(request.getPlate());
        Gate gate = gateService.getGateEntity(request.getGateId());
        VehicleClass vehicleClass = vehicle.getVehicleClass();
        Tariff tariff = tariffService.getTariffEntity(gate.getId(), vehicleClass.getId());

        if (vehicle.getBalance().compareTo(tariff.getFee()) < 0) {
            throw new InsufficientBalanceException("Yetersiz Bakiye! Mevcut bakiye: "+vehicle.getBalance());
        }

        vehicle.setBalance(vehicle.getBalance().subtract(tariff.getFee()));
        Transaction transaction = new Transaction();
        transaction.setVehicleClass(vehicleClass);
        transaction.setGate(gate);
        transaction.setFee(tariff.getFee());
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setVehicle(vehicle);

        Transaction savedTransaction = transactionRepository.save(transaction);
        return transactionHelper.convertToResponse(savedTransaction);
    }

    public List<TransactionResponse> getTransactionsByPlate(String plate) {
        return transactionRepository.findByVehicle_PlateOrderByTransactionDateDesc(plate)
                .stream()
                .map(transactionHelper::convertToResponse)
                .toList();

    }

}