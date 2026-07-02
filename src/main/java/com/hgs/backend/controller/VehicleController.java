package com.hgs.backend.controller;

import com.hgs.backend.dto.VehicleRequest;
import com.hgs.backend.dto.VehicleResponse;
import com.hgs.backend.model.Vehicle;
import com.hgs.backend.service.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    // 1. Yeni Araç Ekleme (Request DTO alır, Response DTO döner)
    @PostMapping
    public ResponseEntity<VehicleResponse> addVehicle(@RequestBody VehicleRequest request) {
        // DTO'dan gelen verileri gerçek Entity nesnemize dönüştürüyoruz (Mapping)
        Vehicle vehicle = new Vehicle();
        vehicle.setPlate(request.getPlate());
        vehicle.setVehicleClass(request.getVehicleClass());
        vehicle.setBalance(request.getBalance());
        vehicle.setOwnerName(request.getOwnerName());

        Vehicle savedVehicle = vehicleService.addVehicle(vehicle);

        // Kaydedilen Entity'yi güvenli bir şekilde Response DTO'ya dönüştürüp dönüyoruz
        return ResponseEntity.ok(convertToResponse(savedVehicle));
    }

    // 2. Plakaya Göre Araç Getirme (Response DTO döner)
    @GetMapping("/{plate}")
    public ResponseEntity<VehicleResponse> getVehicleByPlate(@PathVariable String plate) {
        Vehicle foundVehicle = vehicleService.getVehicleByPlate(plate);
        return ResponseEntity.ok(convertToResponse(foundVehicle));
    }

    // 3. Tüm Araçları Getirme (Response DTO Listesi döner)
    @GetMapping
    public ResponseEntity<List<VehicleResponse>> getAllVehicles() {
        List<Vehicle> allVehicles = vehicleService.getAllVehicles();

        // Tüm listeyi tek tek DTO'ya çeviriyoruz
        List<VehicleResponse> responseList = allVehicles.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseList);
    }

    // 4. PUT: Tüm Aracı Güncelleme
    @PutMapping("/{plate}")
    public ResponseEntity<VehicleResponse> updateVehicle(@PathVariable String plate, @RequestBody VehicleRequest request) {
        Vehicle vehicleData = new Vehicle();
        vehicleData.setOwnerName(request.getOwnerName());
        vehicleData.setVehicleClass(request.getVehicleClass());
        vehicleData.setBalance(request.getBalance());

        Vehicle updatedVehicle = vehicleService.updateVehicle(plate, vehicleData);
        return ResponseEntity.ok(convertToResponse(updatedVehicle));
    }

    // 5. PATCH: Sadece Bakiye Güncelleme
    @PatchMapping("/{plate}/balance")
    public ResponseEntity<VehicleResponse> updateBalance(@PathVariable String plate, @RequestBody com.hgs.backend.dto.BalanceUpdateRequest request) {
        Vehicle updatedVehicle = vehicleService.updateBalance(plate, request.getNewBalance());
        return ResponseEntity.ok(convertToResponse(updatedVehicle));
    }

    // 6. DELETE: Aracı Silme (Geriye içerik dönmediğimiz için 204 No Content veya 202 Accepted dönebiliriz)
    @DeleteMapping("/{plate}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable String plate) {
        vehicleService.deleteVehicle(plate);
        return ResponseEntity.noContent().build(); // 204 No Content durum kodu döner (İşlem başarılı, dönecek gövde yok)
    }

    // Helper (Yardımcı) Metot: Entity'yi Response DTO'ya çevirir
    private VehicleResponse convertToResponse(Vehicle vehicle) {
        VehicleResponse response = new VehicleResponse();
        response.setId(vehicle.getId());
        response.setPlate(vehicle.getPlate());
        response.setVehicleClass(vehicle.getVehicleClass());
        response.setBalance(vehicle.getBalance());
        response.setOwnerName(vehicle.getOwnerName());
        return response;
    }
}