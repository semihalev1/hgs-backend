package com.hgs.backend.controller;

import com.hgs.backend.dto.VehicleRequest;
import com.hgs.backend.dto.VehicleResponse;
import com.hgs.backend.model.Vehicle;
import com.hgs.backend.service.VehicleService;
import com.hgs.backend.util.VehicleHelper;
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
    private final VehicleHelper vehicleHelper;

    @PostMapping
    public ResponseEntity<VehicleResponse> addVehicle(@RequestBody VehicleRequest request) {
        Vehicle vehicle = vehicleHelper.convertToEntity(request);
        Vehicle savedVehicle = vehicleService.addVehicle(vehicle);
        return ResponseEntity.ok(vehicleHelper.convertToResponse(savedVehicle));
    }

    @GetMapping("/{plate}")
    public ResponseEntity<VehicleResponse> getVehicleByPlate(@PathVariable String plate) {
        Vehicle foundVehicle = vehicleService.getVehicleByPlate(plate);
        return ResponseEntity.ok(vehicleHelper.convertToResponse(foundVehicle));
    }

    @GetMapping
    public ResponseEntity<List<VehicleResponse>> getAllVehicles() {
        List<Vehicle> allVehicles = vehicleService.getAllVehicles();

        List<VehicleResponse> responseList = allVehicles.stream()
                .map(vehicleHelper::convertToResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseList);
    }

    @PutMapping("/{plate}")
    public ResponseEntity<VehicleResponse> updateVehicle(@PathVariable String plate, @RequestBody VehicleRequest request) {
        Vehicle vehicleData = vehicleHelper.convertToEntity(request);
        Vehicle updatedVehicle = vehicleService.updateVehicle(plate, vehicleData);
        return ResponseEntity.ok(vehicleHelper.convertToResponse(updatedVehicle));
    }

    @PatchMapping("/{plate}/balance")
    public ResponseEntity<VehicleResponse> updateBalance(@PathVariable String plate, @RequestBody com.hgs.backend.dto.BalanceUpdateRequest request) {
        Vehicle updatedVehicle = vehicleService.updateBalance(plate, request.getNewBalance());
        return ResponseEntity.ok(vehicleHelper.convertToResponse(updatedVehicle));
    }

    @DeleteMapping("/{plate}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable String plate) {
        vehicleService.deleteVehicle(plate);
        return ResponseEntity.noContent().build();
    }

}