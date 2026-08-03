package com.hgs.backend.service;

import com.hgs.backend.dto.vehicle.VehicleClassResponse;
import com.hgs.backend.mapper.VehicleClassMapper;
import com.hgs.backend.exception.VehicleClassNotFoundException;
import com.hgs.backend.model.VehicleClass;
import com.hgs.backend.repository.VehicleClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class VehicleClassService {

    private final VehicleClassRepository vehicleClassRepository;
    private final VehicleClassMapper vehicleClassMapper;

    public VehicleClass getVehicleClassEntity (Long id) {
        return vehicleClassRepository.findById(id)
                .orElseThrow(() -> new VehicleClassNotFoundException(id + " numaralı araç sınıfı bulunamadı"));
    }

    public List<VehicleClassResponse> getAllVehicleClasses() {
        return vehicleClassRepository.findAllByOrderByCodeAsc()
                .stream()
                .map(vehicleClassMapper::convertToResponse)
                .toList();
    }

}
