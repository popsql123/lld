package com.example.parkingLot.strategy;

import com.example.parkingLot.model.Ticket;

public interface PricingStrategy {

    double calculateBill(Ticket ticket);
}
