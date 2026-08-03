package com.hgs.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "tariffs",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_tariffs_gate_vehicle_class",
                        columnNames = {
                                "gate_id",
                                "vehicle_class_id"
                        }
                )
        }
)
public class Tariff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "gate_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_tariffs_gate")
    )
    private Gate gate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "vehicle_class_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_tariffs_vehicle_class")
    )
    private VehicleClass vehicleClass;

    @Column(
            nullable = false,
            precision = 12,
            scale = 2
    )
    private BigDecimal fee;


}
