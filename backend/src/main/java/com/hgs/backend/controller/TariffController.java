package com.hgs.backend.controller;

import com.hgs.backend.dto.TariffQuoteRequest;
import com.hgs.backend.dto.TariffQuoteResponse;
import com.hgs.backend.service.TariffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tariffs")
public class TariffController {

    private final TariffService tariffService;

    @GetMapping("/quote")
    public ResponseEntity<TariffQuoteResponse> getQuote(@Valid @ModelAttribute TariffQuoteRequest request) {
        return ResponseEntity.ok(tariffService.getQuote(request));
    }
}
