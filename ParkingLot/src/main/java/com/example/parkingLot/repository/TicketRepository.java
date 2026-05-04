package com.example.parkingLot.repository;

import com.example.parkingLot.model.Ticket;

import java.util.UUID;

public interface TicketRepository {

    void save(Ticket t);

    Ticket getById(UUID id);

}
