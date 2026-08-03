package com.hgs.backend.controller;

import com.hgs.backend.dto.GateRevenueResponse;
import com.hgs.backend.dto.VehicleClassTrafficResponse;
import com.hgs.backend.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/gate-revenue")
    public ResponseEntity<List<GateRevenueResponse>> getGateRevenue() {
        return ResponseEntity.ok(analyticsService.getGateRevenue());
    }

    @GetMapping("/vehicle-class-traffic")
    public ResponseEntity<List<VehicleClassTrafficResponse>> getVehicleClassTraffic() {
        return ResponseEntity.ok(analyticsService.getVehicleClassTraffic());
    }
}