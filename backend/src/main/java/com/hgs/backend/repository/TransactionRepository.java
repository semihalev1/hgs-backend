package com.hgs.backend.repository;

import com.hgs.backend.model.Transaction;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @EntityGraph(
            attributePaths = {
                    "vehicle",
                    "gate",
                    "vehicleClass"
            }
    )
    List<Transaction>
    findByVehicle_PlateOrderByTransactionDateDesc(
            String plate
    );

}