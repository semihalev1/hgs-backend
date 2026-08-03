package com.hgs.backend.service;

import com.hgs.backend.dto.VehicleClassResponse;
import com.hgs.backend.exception.VehicleClassNotFoundException;
import com.hgs.backend.model.VehicleClass;
import com.hgs.backend.repository.VehicleClassRepository;
import com.hgs.backend.util.VehicleClassHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class VehicleClassService {

    private final VehicleClassRepository vehicleClassRepository;
    private final VehicleClassHelper vehicleClassHelper;

    public VehicleClass getVehicleClassEntity (Long id) {
        return vehicleClassRepository.findById(id)
                .orElseThrow(() -> new VehicleClassNotFoundException(id + " numaralı araç sınıfı bulunamadı"));
    }

    public List<VehicleClassResponse> getAllVehicleClasses() {
        return vehicleClassRepository.findAllByOrderByCodeAsc()
                .stream()
                .map(vehicleClassHelper::convertToResponse)
                .toList();
    }

}
