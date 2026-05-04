package com.example.parkingLot.model;

import com.example.parkingLot.enums.PaymentStatus;
import com.example.parkingLot.exception.ParkingLotException;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

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
        if(paymentStatus != PaymentStatus.UN_PAID)
            throw new ParkingLotException(BAD_REQUEST, BAD_REQUEST.value(), "Payment already done");
        paymentStatus = PaymentStatus.PAID;
    }

    public void markFailed(){
        paymentStatus = PaymentStatus.FAILED;
    }

    public void markExit(){
        exitTime = Instant.now();
    }
}
