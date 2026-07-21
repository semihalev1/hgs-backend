package com.hgs.backend.controller;

import com.hgs.backend.dto.TransactionRequest;
import com.hgs.backend.dto.TransactionResponse;
import com.hgs.backend.model.Transaction;
import com.hgs.backend.service.TransactionService;
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

    @PostMapping("/transaction")
    public ResponseEntity<TransactionResponse> createTransaction(@Valid @RequestBody TransactionRequest request) {
        return ResponseEntity.ok(transactionService.createTransaction(request));
    }

    @GetMapping("/vehicles/{plate}/transaction")
    public ResponseEntity<List<TransactionResponse>> getTransactionsByPlate(@PathVariable String plate) {
        return ResponseEntity.ok(transactionService.getTransactionsByPlate(plate));
    }
}