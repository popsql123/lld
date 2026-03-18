package com.example.parkingLot.model;

import com.example.parkingLot.enums.VehicleType;
import lombok.Data;

import java.util.UUID;

@Data
public class Vehicle {

    UUID id = UUID.randomUUID();
    String vehicleNo;
    VehicleType type;

    public Vehicle(String vehicleId, VehicleType type) {
        this.vehicleNo = vehicleId;
        this.type = type;
    }
}
