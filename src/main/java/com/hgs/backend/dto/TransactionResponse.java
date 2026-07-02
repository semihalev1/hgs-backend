package com.hgs.backend.dto;

import java.time.LocalDateTime;

public class TransactionResponse {
    private Long id;
    private String stationName;
    private Double fee;
    private LocalDateTime transactionDate;
    private String vehiclePlate;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStationName() { return stationName; }
    public void setStationName(String stationName) { this.stationName = stationName; }

    public Double getFee() { return fee; }
    public void setFee(Double fee) { this.fee = fee; }

    public LocalDateTime getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDateTime transactionDate) { this.transactionDate = transactionDate; }

    public String getVehiclePlate() { return vehiclePlate; }
    public void setVehiclePlate(String vehiclePlate) { this.vehiclePlate = vehiclePlate; }
}