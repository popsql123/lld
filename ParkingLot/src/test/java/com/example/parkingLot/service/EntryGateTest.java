package com.example.parkingLot.service;


import com.example.parkingLot.enums.SpotType;
import com.example.parkingLot.model.Floor;
import com.example.parkingLot.model.ParkingLot;
import com.example.parkingLot.model.ParkingSpot;
import com.example.parkingLot.policy.VehicleTypeToSpotPolicy;
import com.example.parkingLot.repository.FloorRepositoryImpl;
import com.example.parkingLot.repository.TicketRepositoryImpl;
import com.example.parkingLot.strategy.implementation.RegularSpotAllocation;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.lang.String.valueOf;


public class EntryGateTest {

    static EntryGate entryGate;

    @BeforeAll()
    public static void setup(){
        entryGate = new EntryGateService(new RegularSpotAllocation(new VehicleTypeToSpotPolicy()),
                new TicketRepositoryImpl(),
                new FloorRepositoryImpl(),
                new ParkingLot("ParkingLotMain", getListOfFloors(5, 10)));
    }

    public static List<Floor> getListOfFloors(int n, int psn){
        List<Floor> l = new ArrayList<>();
        for(int i =0; i<n; i++){
            l.add(new Floor(valueOf(i+1), getParkingSpotsList(i+1, psn)));
        }
        return l;
    }

    private static List<ParkingSpot> getParkingSpotsList(int floorNo, int psn) {
        List<ParkingSpot> l = new ArrayList<>();
        for(int i=0;i<psn/3;i++){
            l.add(new ParkingSpot(valueOf(floorNo*10+i+1), UUID.fromString(valueOf(floorNo)), SpotType.COMPACT));
        }
        for(int i=psn/3;i<psn*2/3;i++){
            l.add(new ParkingSpot(valueOf(floorNo*10+i+1), UUID.fromString(valueOf(floorNo)), SpotType.REGULAR));
        }
        for(int i=psn*2/3;i<psn;i++){
            l.add(new ParkingSpot(valueOf(floorNo*10+i+1), UUID.fromString(valueOf(floorNo)), SpotType.LARGE));
        }
        return l;
    }
}
