package com.example.parkingLot.model;

import com.example.parkingLot.enums.SpotType;
import lombok.Data;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

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
                Queue<ParkingSpot> q = spotsQueues.getOrDefault(ps.getSpotType(), new ConcurrentLinkedQueue<>());
                q.offer(ps);
                spotsQueues.put(ps.getSpotType(), q);
            }
        }
    }

    public boolean isSpotAvailable(SpotType type){
        return spotsQueues.containsKey(type) && !spotsQueues.get(type).isEmpty();
    }

    public Optional<ParkingSpot> allocateSpot(SpotType type){
        if(!isSpotAvailable(type)) return Optional.empty();

        ParkingSpot allocated = spotsQueues.get(type).poll();

        //Note: concurrency issue could have happened while polling, eager check of availability of resource is useless cuz both threads can see one element present but only one can poll
        if(Objects.isNull(allocated)) {
            return Optional.empty();
        }
        if(!allocated.occupy()) {
            spotsQueues.get(type).offer(allocated);
            return Optional.empty();
        }

        return Optional.of(allocated);
    }

    public void releaseSpot(ParkingSpot ps){
        ps.release();
        Queue<ParkingSpot> q = spotsQueues.getOrDefault(ps.getSpotType(), new ConcurrentLinkedQueue<>());
        q.offer(ps);
        spotsQueues.put(ps.getSpotType(), q);
    }

}
