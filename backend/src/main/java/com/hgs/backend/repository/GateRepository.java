package com.hgs.backend.repository;

import com.hgs.backend.model.Gate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GateRepository extends JpaRepository<Gate, Long> {
    List<Gate> findAllByOrderByNameAsc();

}
