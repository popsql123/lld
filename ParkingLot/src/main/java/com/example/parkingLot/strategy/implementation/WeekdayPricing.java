package com.example.parkingLot.strategy.implementation;

import com.example.parkingLot.model.Ticket;
import com.example.parkingLot.strategy.PricingStrategy;

public class WeekdayPricing implements PricingStrategy {

    @Override
    public double calculateBill(Ticket ticket) {
        double bill = 0;
        int duration = ticket.getExitTime() - ticket.getEntryTime();
        switch(ticket.getParkingSpot().getSpotType()){
            case COMPACT -> bill = 50d * duration;
            case REGULAR -> bill = 100d * duration;
            default -> bill = 150d * duration;
        }
        ticket.setPrice(bill);
        return bill;
    }
}
