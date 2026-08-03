package com.hgs.backend.mapper;

import com.hgs.backend.dto.tariff.TariffQuoteResponse;
import com.hgs.backend.model.Gate;
import com.hgs.backend.model.Tariff;
import com.hgs.backend.model.Vehicle;
import org.springframework.stereotype.Component;

@Component
public class TariffMapper {

    public TariffQuoteResponse convertToQuoteResponse(Vehicle vehicle, Gate gate, Tariff tariff) {
        TariffQuoteResponse response = new TariffQuoteResponse();

        response.setFee(tariff.getFee());
        response.setGateCode(gate.getCode());
        response.setGateId(gate.getId());
        response.setGateName(gate.getName());
        response.setVehiclePlate(vehicle.getPlate());
        response.setVehicleClassCode(vehicle.getVehicleClass().getCode());
        response.setVehicleClassId(vehicle.getVehicleClass().getId());
        response.setVehicleClassName(vehicle.getVehicleClass().getName());

        return response;
    }
}
