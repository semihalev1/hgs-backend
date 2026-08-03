package com.hgs.backend.util;

import com.hgs.backend.dto.GateRevenueResponse;
import com.hgs.backend.dto.VehicleClassTrafficResponse;
import com.hgs.backend.repository.projection.GateRevenueProjection;
import com.hgs.backend.repository.projection.VehicleClassTrafficProjection;
import org.springframework.stereotype.Component;

@Component
public class AnalyticsHelper {

    public GateRevenueResponse convertToGateRevenueResponse(
            GateRevenueProjection projection
    ) {
        return new GateRevenueResponse(
                projection.getGateId(),
                projection.getGateCode(),
                projection.getGateName(),
                projection.getTotalRevenue()
        );
    }

    public VehicleClassTrafficResponse convertToVehicleClassTrafficResponse(
            VehicleClassTrafficProjection projection
    ) {
        return new VehicleClassTrafficResponse(
                projection.getVehicleClassId(),
                projection.getVehicleClassCode(),
                projection.getVehicleClassName(),
                projection.getPassageCount()
        );
    }
}
