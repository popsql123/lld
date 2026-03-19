package com.example.parkingLot.strategy.implementation;

import com.example.parkingLot.model.Ticket;
import com.example.parkingLot.strategy.PricingStrategy;
import com.example.parkingLot.utility.TimeUtility;

public class WeekdayPricing implements PricingStrategy {

     TimeUtility timeUtility;

    public WeekdayPricing() {
        this.timeUtility = new TimeUtility();
    }

    @Override
    public double calculateBill(Ticket ticket) {
        double bill = 0;
        long duration = timeUtility.calculateDurationHours(ticket.getExitTime(), ticket.getEntryTime());
        if(duration == 0) duration = 1;
        switch(ticket.getParkingSpot().getSpotType()){
            case COMPACT -> bill = 50d * duration;
            case REGULAR -> bill = 100d * duration;
            default -> bill = 150d * duration;
        }
        ticket.setPrice(bill);
        return bill;
    }
}
