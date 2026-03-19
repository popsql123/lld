package com.example.parkingLot.utility;

import java.time.Duration;
import java.time.Instant;

public class TimeUtility {

    public long calculateDurationHours(Instant endtime, Instant startTime){
        return calculateDuration(endtime, startTime).toHours();
    }

    public Duration calculateDuration(Instant endtime, Instant startTime){
        return Duration.between(startTime, endtime);
    }
}
