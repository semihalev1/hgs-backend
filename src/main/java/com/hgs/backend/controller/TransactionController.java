package com.hgs.backend.controller;

import com.hgs.backend.dto.TransactionRequest;
import com.hgs.backend.dto.TransactionResponse;
import com.hgs.backend.model.Transaction;
import com.hgs.backend.service.TransactionService;
import com.hgs.backend.util.TransactionHelper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionHelper transactionHelper;

    @PostMapping("/transaction")
    public ResponseEntity<TransactionResponse> createTransaction(@Valid @RequestBody TransactionRequest request) {
        Transaction newTransaction = transactionService.createTransaction(request);
        return ResponseEntity.ok(transactionHelper.convertToResponse(newTransaction));
    }

    @GetMapping("/vehicles/{plate}/transaction")
    public ResponseEntity<List<TransactionResponse>> getTransactionsByPlate(@PathVariable String plate) {
        List<Transaction> transactions = transactionService.getTransactionsByPlate(plate);

        List<TransactionResponse> responseList = transactions.stream()
                .map(transactionHelper::convertToResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseList);
    }
}