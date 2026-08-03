package com.hgs.backend.controller;

import com.hgs.backend.dto.transaction.TransactionRequest;
import com.hgs.backend.dto.transaction.TransactionResponse;
import com.hgs.backend.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/transactions")
    public ResponseEntity<TransactionResponse> createTransaction(@Valid @RequestBody TransactionRequest request) {
        return ResponseEntity.ok(transactionService.createTransaction(request));
    }

    @GetMapping("/vehicles/{plate}/transactions")
    public ResponseEntity<List<TransactionResponse>> getTransactionsByPlate(@PathVariable String plate) {
        return ResponseEntity.ok(transactionService.getTransactionsByPlate(plate));
    }
}
