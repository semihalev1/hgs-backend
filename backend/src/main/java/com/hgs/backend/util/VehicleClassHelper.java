package com.hgs.backend.util;

import com.hgs.backend.dto.VehicleClassResponse;
import com.hgs.backend.model.VehicleClass;
import org.springframework.stereotype.Component;

@Component
public class VehicleClassHelper {

    public VehicleClassResponse convertToResponse (VehicleClass vehicleClass) {
        VehicleClassResponse vehicleClassResponse = new VehicleClassResponse();
        vehicleClassResponse.setId(vehicleClass.getId());
        vehicleClassResponse.setCode(vehicleClass.getCode());
        vehicleClassResponse.setName(vehicleClass.getName());

        return vehicleClassResponse;
    }
}
