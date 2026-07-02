package com.hgs.backend.repository;

import com.hgs.backend.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByVehiclePlate(String plate);
}