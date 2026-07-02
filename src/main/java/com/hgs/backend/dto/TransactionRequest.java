package com.hgs.backend.dto;

public class TransactionRequest {
    private String plate;
    private String stationName;
    private Double fee;

    public String getPlate() { return plate; }
    public void setPlate(String plate) { this.plate = plate; }

    public String getStationName() { return stationName; }
    public void setStationName(String stationName) { this.stationName = stationName; }

    public Double getFee() { return fee; }
    public void setFee(Double fee) { this.fee = fee; }
}