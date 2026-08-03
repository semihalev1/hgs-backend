package com.hgs.backend.service;

import com.hgs.backend.dto.analytics.GateRevenueResponse;
import com.hgs.backend.dto.analytics.VehicleClassTrafficResponse;
import com.hgs.backend.mapper.AnalyticsMapper;
import com.hgs.backend.repository.AnalyticsRepository;
import com.hgs.backend.repository.projection.GateRevenueProjection;
import com.hgs.backend.repository.projection.VehicleClassTrafficProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnalyticsService {

    private final AnalyticsRepository analyticsRepository;
    private final AnalyticsMapper analyticsMapper;

    public List<GateRevenueResponse> getGateRevenue() {
        return analyticsRepository.findGateRevenue()
                .stream()
                .map(analyticsMapper::convertToGateRevenueResponse)
                .toList();
    }

    public List<VehicleClassTrafficResponse> getVehicleClassTraffic() {
        return analyticsRepository.findVehicleClassTraffic()
                .stream()
                .map(analyticsMapper::convertToVehicleClassTrafficResponse)
                .toList();
    }

}
