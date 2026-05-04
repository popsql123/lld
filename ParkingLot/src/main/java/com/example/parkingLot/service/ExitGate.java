package com.example.parkingLot.service;

import com.example.parkingLot.model.Ticket;

public interface ExitGate {

    double takeExit(Ticket ticket);

    void pay(Ticket ticket);
}
