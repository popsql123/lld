package com.example.parkingLot.model;

import com.example.parkingLot.enums.PaymentStatus;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class Ticket {

    UUID id  = UUID.randomUUID();
    Vehicle vehicle;
    ParkingSpot parkingSpot;
    PaymentStatus paymentStatus;
    Instant entryTime;
    Instant exitTime;
    double price;

    public Ticket(Vehicle vehicle, ParkingSpot parkingSpot) {
        this.vehicle = vehicle;
        this.parkingSpot = parkingSpot;
        paymentStatus = PaymentStatus.UN_PAID;
        this.entryTime = Instant.now();
    }

    public void markPaid(){
        paymentStatus = PaymentStatus.PAID;
    }

    public void markFailed(){
        paymentStatus = PaymentStatus.FAILED;
    }
}
