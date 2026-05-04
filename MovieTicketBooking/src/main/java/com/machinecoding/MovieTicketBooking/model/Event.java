package com.machinecoding.MovieTicketBooking.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Event {

    UUID id  = UUID.randomUUID();
    String name;
    String genre;
    String lang;

}
