package com.example.parkingLot.policy;

import com.example.parkingLot.enums.SpotType;
import com.example.parkingLot.enums.VehicleType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.parkingLot.enums.SpotType.*;
import static com.example.parkingLot.enums.VehicleType.*;

public class VehicleTypeToSpotPolicy {

    Map<VehicleType, List<SpotType>> map = new HashMap<>();

    public VehicleTypeToSpotPolicy(){
        populateMap();
    }

    private void populateMap(){
        map.put(TWO_WHEELER, Arrays.asList(REGULAR, COMPACT));
        map.put(FOUR_WHEELER, Arrays.asList(COMPACT, LARGE));
        map.put(TRUCK, List.of(LARGE));
    }

    public List<SpotType> getMapping(VehicleType type){
        return map.get(type);
    }
}
