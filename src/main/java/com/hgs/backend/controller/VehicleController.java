package com.hgs.backend.controller;

import com.hgs.backend.dto.VehicleRequest;
import com.hgs.backend.dto.VehicleResponse;
import com.hgs.backend.model.Vehicle;
import com.hgs.backend.service.VehicleService;
import com.hgs.backend.util.VehicleHelper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<VehicleResponse> addVehicle(@Valid @RequestBody VehicleRequest request) {
        return ResponseEntity.ok(vehicleService.addVehicle(request));
    }

    @GetMapping("/{plate}")
    public ResponseEntity<VehicleResponse> getVehicleByPlate(@PathVariable String plate) {
        return ResponseEntity.ok(vehicleService.getVehicleByPlate(plate));
    }

    @GetMapping
    public ResponseEntity<List<VehicleResponse>> getAllVehicles() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }

    @PutMapping("/{plate}")
    public ResponseEntity<VehicleResponse> updateVehicle(@PathVariable String plate, @Valid @RequestBody VehicleRequest request) {
        return ResponseEntity.ok(vehicleService.updateVehicle(plate, request));
    }

    @PatchMapping("/{plate}/balance")
    public ResponseEntity<VehicleResponse> updateBalance(@PathVariable String plate, @Valid @RequestBody com.hgs.backend.dto.BalanceUpdateRequest request) {
        return ResponseEntity.ok(vehicleService.updateBalance(plate, request.getNewBalance()));
    }

    @DeleteMapping("/{plate}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable String plate) {
        vehicleService.deleteVehicle(plate);
        return ResponseEntity.noContent().build();
    }

}