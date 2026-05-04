package com.example.parkingLot.service;

import com.example.parkingLot.enums.VehicleType;
import com.example.parkingLot.model.Ticket;

public interface EntryGate {

    Ticket allocateSpot(String vehicleNo, VehicleType type);
}
