package com.hgs.backend.service;

import com.hgs.backend.dto.TariffQuoteRequest;
import com.hgs.backend.dto.TariffQuoteResponse;
import com.hgs.backend.exception.TariffNotFoundException;
import com.hgs.backend.model.Gate;
import com.hgs.backend.model.Tariff;
import com.hgs.backend.model.Vehicle;
import com.hgs.backend.model.VehicleClass;
import com.hgs.backend.repository.TariffRepository;
import com.hgs.backend.util.TariffHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TariffService {

    private final TariffRepository tariffRepository;
    private final VehicleService vehicleService;
    private final GateService gateService;
    private final TariffHelper tariffHelper;
    public Tariff getTariffEntity(Long gateId, Long vehicleClassId) {
        return tariffRepository.findByGate_IdAndVehicleClass_Id(gateId, vehicleClassId)
                .orElseThrow(() -> new TariffNotFoundException(" Seçilen gişe ve araç sınıfı için tarife bulunamadı."));
    }

    @Transactional(readOnly = true)
    public TariffQuoteResponse getQuote (TariffQuoteRequest request) {
        Vehicle vehicle = vehicleService.getVehicleEntity(request.getPlate());

        Gate gate = gateService.getGateEntity(request.getGateId());

        VehicleClass vehicleClass = vehicle.getVehicleClass();

        Tariff tariff = getTariffEntity(gate.getId(), vehicleClass.getId());

        return tariffHelper.convertToQuoteResponse(vehicle, gate, tariff);

    }
}
