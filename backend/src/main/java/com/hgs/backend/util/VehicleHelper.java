package com.hgs.backend.util;

import com.hgs.backend.dto.VehicleRequest;
import com.hgs.backend.dto.VehicleResponse;
import com.hgs.backend.model.Vehicle;
import org.springframework.stereotype.Component;

@Component
public class VehicleHelper {

    public VehicleResponse convertToResponse(Vehicle vehicle) {
        VehicleResponse vehicleResponse = new VehicleResponse();
        vehicleResponse.setId(vehicle.getId());
        vehicleResponse.setBalance(vehicle.getBalance());
        vehicleResponse.setPlate(vehicle.getPlate());
        vehicleResponse.setVehicleClass(vehicle.getVehicleClass());
        vehicleResponse.setOwnerName(vehicle.getOwnerName());
        return vehicleResponse;
    }

    public Vehicle convertToEntity(VehicleRequest vehicleRequest) {
        Vehicle vehicle = new Vehicle();
        vehicle.setPlate(vehicleRequest.getPlate());
        vehicle.setVehicleClass(vehicleRequest.getVehicleClass());
        vehicle.setBalance(vehicleRequest.getBalance());
        vehicle.setOwnerName(vehicleRequest.getOwnerName());
        return vehicle;
    }
}
