package com.hgs.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "gate_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_transactions_gate"
            )
    )
    private Gate gate;

    @Column(
            nullable = false,
            precision = 12,
            scale = 2
    )
    private BigDecimal fee;

    @Column(
            name = "transaction_date",
            nullable = false
    )
    private LocalDateTime transactionDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "vehicle_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_transactions_vehicle"
            )
    )
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "vehicle_class_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_transactions_vehicle_class"
            )
    )
    private VehicleClass vehicleClass;

}