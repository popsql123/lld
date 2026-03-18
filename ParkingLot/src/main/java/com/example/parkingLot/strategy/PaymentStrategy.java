package com.example.parkingLot.strategy;

import com.example.parkingLot.model.Ticket;

public interface PaymentStrategy {

    public void pay(Ticket ticket);
}
