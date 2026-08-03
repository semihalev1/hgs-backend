package com.hgs.backend.repository;

import com.hgs.backend.model.Vehicle;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    boolean existsByPlate(String plate);

    @EntityGraph(attributePaths = "vehicleClass")
    Optional<Vehicle> findByPlate(String plate);

    @EntityGraph(attributePaths = "vehicleClass")
    List<Vehicle> findAllByOrderByPlateAsc();
}