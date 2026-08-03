package com.hgs.backend.util;

import com.hgs.backend.dto.VehicleRequest;
import com.hgs.backend.dto.VehicleResponse;
import com.hgs.backend.model.Vehicle;
import com.hgs.backend.model.VehicleClass;
import org.springframework.stereotype.Component;

@Component
public class VehicleHelper {

    public VehicleResponse convertToResponse(Vehicle vehicle) {
        VehicleResponse response = new VehicleResponse();

        response.setId(vehicle.getId());
        response.setPlate(vehicle.getPlate());
        response.setBalance(vehicle.getBalance());
        response.setOwnerName(vehicle.getOwnerName());

        VehicleClass vehicleClass = vehicle.getVehicleClass();
        response.setVehicleClassId(vehicleClass.getId());
        response.setVehicleClassCode(vehicleClass.getCode());
        response.setVehicleClassName(vehicleClass.getName());

        return response;
    }

    public Vehicle convertToEntity(VehicleRequest request, VehicleClass vehicleClass) {
        Vehicle vehicle = new Vehicle();

        vehicle.setPlate(request.getPlate());
        vehicle.setVehicleClass(vehicleClass);
        vehicle.setBalance(request.getBalance());
        vehicle.setOwnerName(request.getOwnerName());

        return vehicle;
    }
}
