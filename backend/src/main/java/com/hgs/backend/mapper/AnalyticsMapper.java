package com.hgs.backend.mapper;

import com.hgs.backend.dto.analytics.GateRevenueResponse;
import com.hgs.backend.dto.analytics.VehicleClassTrafficResponse;
import com.hgs.backend.repository.projection.GateRevenueProjection;
import com.hgs.backend.repository.projection.VehicleClassTrafficProjection;
import org.springframework.stereotype.Component;

@Component
public class AnalyticsMapper {

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
