package com.example.parkingLot.repository;

import com.example.parkingLot.model.ParkingSpot;
import com.example.parkingLot.model.Ticket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ParkingSpotRepository {

    Map<UUID, ParkingSpot> store;

    public ParkingSpotRepository() {
        this.store = new ConcurrentHashMap<>();
    }

    public ParkingSpotRepository(List<ParkingSpot> elements) {
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
