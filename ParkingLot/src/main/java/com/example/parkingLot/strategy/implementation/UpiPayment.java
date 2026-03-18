package com.example.parkingLot.strategy.implementation;

import com.example.parkingLot.model.Ticket;
import com.example.parkingLot.strategy.PaymentStrategy;

public class UpiPayment implements PaymentStrategy {

    @Override
    public void pay(Ticket ticket) {
        System.out.println("bill of rupee "+ String.valueOf(ticket.getPrice()) +"paid using upi");
    }
}
