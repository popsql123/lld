package com.example.parkingLot.repository;

import com.example.parkingLot.model.ParkingSpot;

import java.util.UUID;

public interface ParkingSpotRepository {

    void save(ParkingSpot t);

    ParkingSpot getById(UUID id);

}
