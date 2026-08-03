package com.hgs.backend.repository;

import com.hgs.backend.model.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TariffRepository extends JpaRepository<Tariff, Long> {
    Optional<Tariff> findByGate_IdAndVehicleClass_Id(
            Long gateId,
            Long vehicleClassId
    );
}
