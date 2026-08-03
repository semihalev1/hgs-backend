package com.hgs.backend.repository.projection;

public interface VehicleClassTrafficProjection {

    Long getVehicleClassId();

    String getVehicleClassCode();

    String getVehicleClassName();

    Long getPassageCount();
}