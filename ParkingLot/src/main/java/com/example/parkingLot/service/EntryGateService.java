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

import static org.springframework.http.HttpStatus.EXPECTATION_FAILED;

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

        ParkingSpot spot = spotAllocationStrategy.allocateSpot(parkingLot, type)
                .orElseThrow(() -> new ParkingLotException(EXPECTATION_FAILED, EXPECTATION_FAILED.value(), "No Spot Available"));

        try {
            Ticket ticket = new Ticket(vehicle, spot);
            ticketRepository.save(ticket);
            return ticket;
        } catch (Exception ex){
            floorRepository.getById(spot.getFloorId()).releaseSpot(spot);
            throw ex;
        }
    }


}
