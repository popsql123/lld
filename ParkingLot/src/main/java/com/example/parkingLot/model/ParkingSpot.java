package com.example.parkingLot.model;

import com.example.parkingLot.enums.SpotType;
import lombok.Data;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@Data
public class ParkingSpot {

    UUID id = UUID.randomUUID();
    String spotNo;
    UUID floorId;
    SpotType spotType;
    AtomicBoolean occupied;

    public ParkingSpot(String spotNo, UUID floorId, SpotType spotType) {
        this.spotNo = spotNo;
        this.floorId = floorId;
        this.spotType = spotType;
        this.occupied = new AtomicBoolean(false);
    }

    public boolean occupy(){
        return occupied.compareAndSet(false, true);
    }

    public void release(){
        occupied.compareAndSet(true, false);
    }
}
