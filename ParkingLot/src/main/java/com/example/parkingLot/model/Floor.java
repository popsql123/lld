package com.example.parkingLot.model;

import com.example.parkingLot.enums.SpotType;
import lombok.Data;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Data
public class Floor {

    UUID id = UUID.randomUUID();
    String floorNo;
    Map<SpotType, Queue<ParkingSpot>> spotsQueues;

    public Floor(String floorNo, List<ParkingSpot> spotsList){
        this.floorNo = floorNo;
        spotsQueues = new ConcurrentHashMap<>();
        if(!spotsList.isEmpty()){
            for(ParkingSpot ps: spotsList){
                Queue<ParkingSpot> q = spotsQueues.getOrDefault(ps.getSpotType(), new ArrayDeque<>());
                q.offer(ps);
                spotsQueues.put(ps.getSpotType(), q);
            }
        }
    }

    public boolean isSpotAvailable(SpotType type){
        return spotsQueues.containsKey(type) && !spotsQueues.get(type).isEmpty();
    }

    public Optional<ParkingSpot> bookSpot(SpotType type){
        if(!isSpotAvailable(type)) return Optional.empty();

        ParkingSpot allocated = spotsQueues.get(type).poll();
        if(allocated.occupy()) return Optional.of(allocated);

        return Optional.empty();
    }

    public void releaseSpot(ParkingSpot ps){
        ps.release();
        Queue<ParkingSpot> q = spotsQueues.getOrDefault(ps.getSpotType(), new ArrayDeque<>());
        q.offer(ps);
        spotsQueues.put(ps.getSpotType(), q);
    }

}
