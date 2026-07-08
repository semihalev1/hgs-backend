package com.hgs.backend.service;

import com.hgs.backend.model.Vehicle;
import com.hgs.backend.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public Vehicle addVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public Vehicle getVehicleByPlate(String plate) {
        return vehicleRepository.findByPlate(plate)
                .orElseThrow(() -> new RuntimeException("Sistemde bu plakaya ait bir araç bulunamadı: " + plate));
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Vehicle updateVehicle(String plate, Vehicle updatedVehicleData) {
        Vehicle existingVehicle = getVehicleByPlate(plate);
        existingVehicle.setOwnerName(updatedVehicleData.getOwnerName());
        existingVehicle.setVehicleClass(updatedVehicleData.getVehicleClass());
        existingVehicle.setBalance(updatedVehicleData.getBalance());
        return vehicleRepository.save(existingVehicle);
    }

    public Vehicle updateBalance(String plate, Double newBalance) {
        Vehicle existingVehicle = getVehicleByPlate(plate);
        existingVehicle.setBalance(newBalance);
        return vehicleRepository.save(existingVehicle);
    }

    @org.springframework.transaction.annotation.Transactional
    public void deleteVehicle(String plate) {
        Vehicle existingVehicle = getVehicleByPlate(plate);
        vehicleRepository.delete(existingVehicle);
    }
}