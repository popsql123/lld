package com.machinecoding.MovieTicketBooking.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Seat {

    UUID id = UUID.randomUUID();
    String seatNo;
    String screenId;

}
