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
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String plate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "vehicle_class_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_vehicles_vehicle_class"
            )
    )
    private VehicleClass vehicleClass;

    @Column(
            nullable = false,
            precision = 12,
            scale = 2
    )
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(
            name = "owner_name",
            nullable = false,
            length = 150
    )
    private String ownerName;

}