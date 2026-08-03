package com.hgs.backend.controller;

import com.hgs.backend.dto.gate.GateResponse;

import com.hgs.backend.service.GateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/gates")
public class GateController {

    private final GateService gateService;

    @GetMapping
    public ResponseEntity<List<GateResponse>> getAllGates() {
        return ResponseEntity.ok(gateService.getAllGates());
    }

}
