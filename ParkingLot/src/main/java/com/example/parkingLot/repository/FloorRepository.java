package com.example.parkingLot.repository;

import com.example.parkingLot.model.Floor;

import java.util.UUID;

public interface FloorRepository {

    void save(Floor t);

    Floor getById(UUID id);
}
