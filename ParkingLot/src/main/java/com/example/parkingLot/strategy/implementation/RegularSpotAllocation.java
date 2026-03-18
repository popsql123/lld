package com.example.parkingLot.strategy.implementation;

import com.example.parkingLot.enums.SpotType;
import com.example.parkingLot.enums.VehicleType;
import com.example.parkingLot.model.ParkingLot;
import com.example.parkingLot.model.ParkingSpot;
import com.example.parkingLot.policy.VehicleTypeToSpotPolicy;
import com.example.parkingLot.strategy.SpotAllocationStrategy;

import java.util.Optional;

public class RegularSpotAllocation implements SpotAllocationStrategy {

    private final VehicleTypeToSpotPolicy vehicleToSpotPolicy;

    public RegularSpotAllocation(VehicleTypeToSpotPolicy vehicleToSpotPolicy) {
        this.vehicleToSpotPolicy = new VehicleTypeToSpotPolicy();
    }

    @Override
    public Optional<ParkingSpot> allocateSpot(ParkingLot parkingLot, VehicleType type) {
        for(SpotType st: vehicleToSpotPolicy.getMapping(type)){
            Optional<ParkingSpot> parkingSpot = parkingLot.allocateSpot(st);
            if(parkingSpot.isPresent()) return parkingSpot;
        }
        return Optional.empty();
    }
}
