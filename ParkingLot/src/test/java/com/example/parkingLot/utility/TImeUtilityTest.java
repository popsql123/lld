package com.example.parkingLot.utility;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TImeUtilityTest {

    TimeUtility timeUtility;

    TImeUtilityTest(){
        this.timeUtility = new TimeUtility();
    }

    @Test
    public void durationInHoursTest(){

        long actualDuration = timeUtility.calculateDurationHours(Instant.ofEpochSecond(1007200), Instant.ofEpochSecond(1000000));
        assertEquals(2, actualDuration);
    }
}
