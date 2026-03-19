package com.example.parkingLot.strategy.implementation;

import com.example.parkingLot.model.Ticket;
import com.example.parkingLot.strategy.PricingStrategy;
import com.example.parkingLot.utility.TimeUtility;

public class WeekendPricing implements PricingStrategy {

    TimeUtility timeUtility;

    public WeekendPricing() {
        this.timeUtility = new TimeUtility();
    }

    @Override
    public double calculateBill(Ticket ticket) {
        double bill = 0;
        long duration = timeUtility.calculateDurationHours(ticket.getExitTime(), ticket.getEntryTime());
        if(duration == 0) duration = 1;
        switch(ticket.getParkingSpot().getSpotType()){
            case COMPACT -> bill = 70d * duration;
            case REGULAR -> bill = 120d * duration;
            default -> bill = 200d * duration;
        }
        ticket.setPrice(bill);
        return bill;
    }
}
