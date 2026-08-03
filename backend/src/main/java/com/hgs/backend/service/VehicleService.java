package com.hgs.backend.service;

import com.hgs.backend.dto.VehicleRequest;
import com.hgs.backend.dto.VehicleResponse;
import com.hgs.backend.exception.VehicleAlreadyExistException;
import com.hgs.backend.exception.VehicleNotFoundException;
import com.hgs.backend.model.Vehicle;
import com.hgs.backend.model.VehicleClass;
import com.hgs.backend.repository.VehicleRepository;
import com.hgs.backend.util.VehicleHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleHelper vehicleHelper;
    private final VehicleClassService vehicleClassService;

    @Transactional
    public VehicleResponse addVehicle(VehicleRequest request) {
        if (vehicleRepository.existsByPlate(request.getPlate())) {
            throw new VehicleAlreadyExistException(request.getPlate() + " plakalı araç sistemde kayıtlı.");
        }

        VehicleClass vehicleClass = vehicleClassService.getVehicleClassEntity(request.getVehicleClassId());

        Vehicle vehicle = vehicleHelper.convertToEntity(request, vehicleClass);

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
        return vehicleRepository.findAllByOrderByPlateAsc()
                .stream()
                .map(vehicleHelper::convertToResponse)
                .toList();
    }

    @Transactional
    public VehicleResponse updateVehicle(String plate, VehicleRequest request) {
        Vehicle existingVehicle = getVehicleEntity(plate);

        VehicleClass vehicleClass = vehicleClassService.getVehicleClassEntity(request.getVehicleClassId());

        existingVehicle.setOwnerName(request.getOwnerName());
        existingVehicle.setVehicleClass(vehicleClass);
        existingVehicle.setBalance(request.getBalance());

        Vehicle updatedVehicle = vehicleRepository.save(existingVehicle);
        return vehicleHelper.convertToResponse(updatedVehicle);
    }

    @Transactional
    public VehicleResponse loadBalance(String plate, BigDecimal amount) {
        Vehicle existingVehicle = getVehicleEntity(plate);

        existingVehicle.setBalance(existingVehicle.getBalance().add(amount));

        Vehicle updatedVehicle = vehicleRepository.save(existingVehicle);
        return vehicleHelper.convertToResponse(updatedVehicle);
    }

    @Transactional
    public void deleteVehicle(String plate) {
        Vehicle existingVehicle = getVehicleEntity(plate);
        vehicleRepository.delete(existingVehicle);
    }
}