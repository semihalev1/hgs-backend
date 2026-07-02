package com.hgs.backend.dto;

public class VehicleRequest {
    private String plate;
    private String vehicleClass;
    private Double balance;
    private String ownerName;

    public String getPlate() { return plate; }
    public void setPlate(String plate) { this.plate = plate; }

    public String getVehicleClass() { return vehicleClass; }
    public void setVehicleClass(String vehicleClass) { this.vehicleClass = vehicleClass; }

    public Double getBalance() { return balance; }
    public void setBalance(Double balance) { this.balance = balance; }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
}