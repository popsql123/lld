package com.example.parkingLot.service;

import com.example.parkingLot.enums.VehicleType;
import com.example.parkingLot.exception.ParkingLotException;
import com.example.parkingLot.model.ParkingLot;
import com.example.parkingLot.model.ParkingSpot;
import com.example.parkingLot.model.Ticket;
import com.example.parkingLot.model.Vehicle;
import com.example.parkingLot.repository.FloorRepository;
import com.example.parkingLot.repository.TicketRepository;
import com.example.parkingLot.strategy.SpotAllocationStrategy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class EntryGateService {

    SpotAllocationStrategy spotAllocationStrategy;
    TicketRepository ticketRepository;
    FloorRepository floorRepository;
    ParkingLot parkingLot;

    public EntryGateService(SpotAllocationStrategy spotAllocationStrategy, TicketRepository ticketRepository, FloorRepository floorRepository, ParkingLot parkingLot) {
        this.spotAllocationStrategy = spotAllocationStrategy;
        this.ticketRepository = ticketRepository;
        this.floorRepository = floorRepository;
        this.parkingLot = parkingLot;
    }

    //@Transactional
    public Ticket allocateSpot(String vehicleNo, VehicleType type) {
        Vehicle vehicle = new Vehicle(vehicleNo, type);

        Optional<ParkingSpot> spot = spotAllocationStrategy.allocateSpot(parkingLot, type);
        if(spot.isEmpty())
            throw new ParkingLotException(HttpStatus.EXPECTATION_FAILED, "No Spot Available");

        try {
            Ticket ticket = new Ticket(vehicle, spot.get());
            ticketRepository.save(ticket);
            return ticket;
        } catch (Exception ex){
            floorRepository.getById(spot.get().getFloorId()).releaseSpot(spot.get());
            throw ex;
        }
    }


}
