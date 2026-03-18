package com.example.parkingLot.strategy;

import com.example.parkingLot.enums.SpotType;
import com.example.parkingLot.enums.VehicleType;
import com.example.parkingLot.model.ParkingLot;
import com.example.parkingLot.model.ParkingSpot;

import java.util.Optional;

public interface SpotAllocationStrategy {

    public Optional<ParkingSpot> allocateSpot(ParkingLot parkingLot, VehicleType type);
}
