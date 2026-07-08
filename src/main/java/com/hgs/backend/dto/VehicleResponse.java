package com.hgs.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleResponse {

    private Long id;
    private String plate;
    private String vehicleClass;
    private Double balance;
    private String ownerName;

}