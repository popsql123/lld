package com.example.parkingLot.service;

import com.example.parkingLot.exception.ParkingLotException;
import com.example.parkingLot.model.Ticket;
import com.example.parkingLot.repository.FloorRepository;
import com.example.parkingLot.repository.TicketRepository;
import com.example.parkingLot.strategy.PaymentStrategy;
import com.example.parkingLot.strategy.PricingStrategy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
public class ExitGateService {

    TicketRepository ticketRepository;
    PricingStrategy pricingStrategy;
    PaymentStrategy paymentStrategy;
    FloorRepository floorRepository;

    public ExitGateService(TicketRepository ticketRepository, PricingStrategy pricingStrategy, PaymentStrategy paymentStrategy, FloorRepository floorRepository) {
        this.ticketRepository = ticketRepository;
        this.pricingStrategy = pricingStrategy;
        this.paymentStrategy = paymentStrategy;
        this.floorRepository = floorRepository;
    }

    public double takeExit(Ticket ticket){
        if(Objects.isNull(ticketRepository.getById(ticket.getId())))
            throw new ParkingLotException(UNAUTHORIZED, UNAUTHORIZED.value(), "Invalid Ticket");
        ticket.setExitTime(Instant.now());
        floorRepository.getById(ticket.getParkingSpot().getFloorId())
                .releaseSpot(ticket.getParkingSpot());
        return pricingStrategy.calculateBill(ticket);
    }

    public void pay(Ticket ticket){
        try {
            paymentStrategy.pay(ticket);
            ticket.markPaid();
        } catch(Exception ex) {
            ticket.markFailed();
        }
    }


}
