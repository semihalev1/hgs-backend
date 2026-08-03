package com.hgs.backend.dto.analytics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleClassTrafficResponse {

    private Long vehicleClassId;
    private String vehicleClassCode;
    private String vehicleClassName;
    private Long passageCount;
}
