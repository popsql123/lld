package com.example.parkingLot.repository;

import com.example.parkingLot.model.Floor;
import com.example.parkingLot.model.Ticket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class FloorRepository {

    Map<UUID, Floor> store;

    public FloorRepository() {
        this.store = new ConcurrentHashMap<>();
        ;
    }

    public FloorRepository(List<Floor> elements) {
        store = new HashMap<>();
        for (Floor e : elements) {
            store.put(e.getId(), e);
        }
    }

    public void save(Floor t) {
        store.put(t.getId(), t);
    }

    public Floor getById(UUID id){
        return store.getOrDefault(id, null);
    }
}
