package com.example.parkingLot.strategy.implementation;

import com.example.parkingLot.model.Ticket;
import com.example.parkingLot.strategy.PricingStrategy;

public class WeekendPricing implements PricingStrategy {

    @Override
    public double calculateBill(Ticket ticket) {
        switch(ticket.getParkingSpot().getSpotType()){
            case COMPACT -> {
                return 70d;
            }
            case REGULAR -> {
                return 120d;
            }
            default -> {
                return 200d;
            }

        }
    }
}
