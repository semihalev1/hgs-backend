package com.hgs.backend.service;

import com.hgs.backend.dto.GateRevenueResponse;
import com.hgs.backend.dto.VehicleClassTrafficResponse;
import com.hgs.backend.repository.AnalyticsRepository;
import com.hgs.backend.repository.projection.GateRevenueProjection;
import com.hgs.backend.repository.projection.VehicleClassTrafficProjection;
import com.hgs.backend.util.AnalyticsHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnalyticsService {

    private final AnalyticsRepository analyticsRepository;
    private final AnalyticsHelper analyticsHelper;

    public List<GateRevenueResponse> getGateRevenue() {
        return analyticsRepository.findGateRevenue()
                .stream()
                .map(analyticsHelper::convertToGateRevenueResponse)
                .toList();
    }

    public List<VehicleClassTrafficResponse> getVehicleClassTraffic() {
        return analyticsRepository.findVehicleClassTraffic()
                .stream()
                .map(analyticsHelper::convertToVehicleClassTrafficResponse)
                .toList();
    }

}
