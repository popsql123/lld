package com.machinecoding.MovieTicketBooking.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Screen {

    UUID id = UUID.randomUUID();
    String screenNo;
    UUID cinemaId;

}
