package com.example.parkingLot.repository;

import com.example.parkingLot.model.Ticket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TicketRepository {

    Map<UUID, Ticket> store;

    public TicketRepository() {
        this.store = new ConcurrentHashMap<>();
    }

    public TicketRepository(List<Ticket> elements) {
        store = new HashMap<>();
        for (Ticket e : elements) {
            store.put(e.getId(), e);
        }
    }

    public void save(Ticket t) {
        store.put(t.getId(), t);
    }

    public Ticket getById(UUID id){
        return store.getOrDefault(id, null);
    }
}