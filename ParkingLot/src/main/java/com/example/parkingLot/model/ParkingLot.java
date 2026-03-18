package com.example.parkingLot.model;

import com.example.parkingLot.enums.SpotType;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
public class ParkingLot {

    UUID id = UUID.randomUUID();
    String name;
    List<Floor> floors;

    public ParkingLot(String name, List<Floor> floors){
        this.name = name;
        this.floors = floors.isEmpty() ? new ArrayList<>() : floors;
    }

    public void addFloor(Floor floor){
        floors.add(floor);
    }

    public Optional<ParkingSpot> allocateSpot(SpotType type){
        for(Floor floor: floors){
            Optional<ParkingSpot> spot = floor.bookSpot(type);
            if(spot.isPresent()) return spot;
        }
        return Optional.empty();
    }

    //remove floor //update floor name
}
