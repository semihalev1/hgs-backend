package com.hgs.backend.mapper;

import com.hgs.backend.dto.vehicle.VehicleRequest;
import com.hgs.backend.dto.vehicle.VehicleResponse;
import com.hgs.backend.model.Vehicle;
import com.hgs.backend.model.VehicleClass;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {

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
