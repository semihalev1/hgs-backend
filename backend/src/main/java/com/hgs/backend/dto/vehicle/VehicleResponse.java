package com.hgs.backend.dto.vehicle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleResponse {

    private Long id;
    private String plate;
    private Long vehicleClassId;
    private String vehicleClassCode;
    private String vehicleClassName;
    private BigDecimal balance;
    private String ownerName;

}
