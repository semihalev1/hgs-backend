package com.hgs.backend.repository;

import com.hgs.backend.model.VehicleClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehicleClassRepository extends JpaRepository<VehicleClass, Long> {
    Optional<VehicleClass> findByCode(String code);
    List<VehicleClass> findAllByOrderByCodeAsc();
}
