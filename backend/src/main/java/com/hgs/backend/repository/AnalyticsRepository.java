package com.hgs.backend.repository;

import com.hgs.backend.model.Transaction;
import com.hgs.backend.repository.projection.GateRevenueProjection;
import com.hgs.backend.repository.projection.VehicleClassTrafficProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface AnalyticsRepository extends Repository<Transaction, Long> {

    @Query(value = """
              SELECT
                  g.id AS "gateId",
                  g.code AS "gateCode",
                  g.name AS "gateName",
                  COALESCE(SUM(t.fee), 0) AS "totalRevenue"
              FROM gates g
              LEFT JOIN transactions t ON t.gate_id = g.id
              GROUP BY g.id, g.code, g.name
              ORDER BY COALESCE(SUM(t.fee), 0) DESC, g.name ASC
              """, nativeQuery = true)
    List<GateRevenueProjection> findGateRevenue();

    @Query(value = """
              SELECT
                  vc.id AS "vehicleClassId",
                  vc.code AS "vehicleClassCode",
                  vc.name AS "vehicleClassName",
                  COUNT(t.id) AS "passageCount"
              FROM vehicle_classes vc
              LEFT JOIN transactions t ON t.vehicle_class_id = vc.id
              GROUP BY vc.id, vc.code, vc.name
              ORDER BY vc.code ASC
              """, nativeQuery = true)
    List<VehicleClassTrafficProjection> findVehicleClassTraffic();
}
