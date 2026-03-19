package com.example.parkingLot.repository;

import com.example.parkingLot.model.ParkingSpot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ParkingSpotRepositoryImpl implements ParkingSpotRepository{

    Map<UUID, ParkingSpot> store;

    public ParkingSpotRepositoryImpl() {
        this.store = new ConcurrentHashMap<>();
    }

    public ParkingSpotRepositoryImpl(List<ParkingSpot> elements) {
        store = new HashMap<>();
        for (ParkingSpot e : elements) {
            store.put(e.getId(), e);
        }
    }

    public void save(ParkingSpot t) {
        store.put(t.getId(), t);
    }

    public ParkingSpot getById(UUID id){
        return store.getOrDefault(id, null);
    }
}
