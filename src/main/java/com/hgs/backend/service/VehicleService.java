package com.hgs.backend.service;

import com.hgs.backend.dto.VehicleRequest;
import com.hgs.backend.dto.VehicleResponse;
import com.hgs.backend.exception.VehicleAlreadyExistException;
import com.hgs.backend.exception.VehicleNotFoundException;
import com.hgs.backend.model.Vehicle;
import com.hgs.backend.repository.VehicleRepository;
import com.hgs.backend.util.VehicleHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleHelper vehicleHelper;

    public VehicleResponse addVehicle(VehicleRequest vehicleRequest) {
        if(vehicleRepository.existsByPlate(vehicleRequest.getPlate())){
            throw new VehicleAlreadyExistException(vehicleRequest.getPlate()+" plakalı araç sistemde kayıtlı.");
        }
        Vehicle vehicle = vehicleHelper.convertToEntity(vehicleRequest);
        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return vehicleHelper.convertToResponse(savedVehicle);
    }
    public Vehicle getVehicleEntity(String plate) {
        return vehicleRepository.findByPlate(plate)
                .orElseThrow(() -> new VehicleNotFoundException(plate + " plakalı araç bulunamadı"));
    }

    public VehicleResponse getVehicleByPlate(String plate) {
        Vehicle vehicle = getVehicleEntity(plate);
        return vehicleHelper.convertToResponse(vehicle);
    }

    public List<VehicleResponse> getAllVehicles() {
        return vehicleRepository.findAll()
                .stream()
                .map(vehicleHelper::convertToResponse)
                .collect(Collectors.toList());
    }

    public VehicleResponse updateVehicle(String plate, VehicleRequest request) {
        Vehicle existingVehicle = getVehicleEntity(plate);

        existingVehicle.setOwnerName(request.getOwnerName());
        existingVehicle.setVehicleClass(request.getVehicleClass());
        existingVehicle.setBalance(request.getBalance());

        Vehicle updatedVehicle = vehicleRepository.save(existingVehicle);

        return vehicleHelper.convertToResponse(updatedVehicle);
    }

    public VehicleResponse updateBalance(String plate, Double newBalance) {
        Vehicle existingVehicle = getVehicleEntity(plate);
        existingVehicle.setBalance(newBalance);
        Vehicle updatedVehicle = vehicleRepository.save(existingVehicle);
        return vehicleHelper.convertToResponse(updatedVehicle);
    }

    @Transactional
    public void deleteVehicle(String plate) {
        Vehicle existingVehicle = getVehicleEntity(plate);
        vehicleRepository.delete(existingVehicle);
    }
}