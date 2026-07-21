package com.hgs.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
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

    @Column(nullable = false)
    private String vehicleClass;

    @Column(nullable = false)
    private Double balance;

    @Column(nullable = false)
    private String ownerName;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

}