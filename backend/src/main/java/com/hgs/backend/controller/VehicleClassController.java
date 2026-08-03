package com.hgs.backend.controller;

import com.hgs.backend.dto.vehicle.VehicleClassResponse;
import com.hgs.backend.service.VehicleClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/vehicle-classes")
public class VehicleClassController {

    private final VehicleClassService vehicleClassService;
    @GetMapping
    public ResponseEntity<List<VehicleClassResponse>> getAllVehicleClasses() {
        return ResponseEntity.ok(vehicleClassService.getAllVehicleClasses());
    }
}
