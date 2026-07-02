package com.hgs.backend.controller;

import com.hgs.backend.dto.TransactionRequest;
import com.hgs.backend.dto.TransactionResponse;
import com.hgs.backend.model.Transaction;
import com.hgs.backend.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // 1. Yeni Geçiş İşlemi (Response DTO döner)
    @PostMapping("/transaction")
    public ResponseEntity<TransactionResponse> createTransaction(@RequestBody TransactionRequest request) {
        Transaction newTransaction = transactionService.createTransaction(request);
        return ResponseEntity.ok(convertToResponse(newTransaction));
    }

    // 2. Geçmiş Geçişleri Listeleme (Response DTO Listesi döner)
    @GetMapping("/vehicles/{plate}/transaction")
    public ResponseEntity<List<TransactionResponse>> getTransactionsByPlate(@PathVariable String plate) {
        List<Transaction> transactions = transactionService.getTransactionsByPlate(plate);

        List<TransactionResponse> responseList = transactions.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseList);
    }

    // Helper Metot: Entity'yi sonsuz döngüden uzak temiz bir DTO'ya çevirir
    private TransactionResponse convertToResponse(Transaction transaction) {
        TransactionResponse response = new TransactionResponse();
        response.setId(transaction.getId());
        response.setStationName(transaction.getStationName());
        response.setFee(transaction.getFee());
        response.setTransactionDate(transaction.getTransactionDate());

        // Araç nesnesinin içinden sadece plakayı cımbızla çekiyoruz
        if (transaction.getVehicle() != null) {
            response.setVehiclePlate(transaction.getVehicle().getPlate());
        }

        return response;
    }
}