package com.machinecoding.MovieTicketBooking.model;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Data
public class ShowSeat {

    UUID id = UUID.randomUUID();
    UUID showId;
    UUID seatId;
    AtomicBoolean occupied;
    AtomicReference<Instant> expiredAt;


}
