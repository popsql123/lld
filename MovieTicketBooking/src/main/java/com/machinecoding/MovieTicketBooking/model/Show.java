package com.machinecoding.MovieTicketBooking.model;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class Show {

    UUID id = UUID.randomUUID();
    Instant startingTime;
    Instant endingTime;

}
