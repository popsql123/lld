package com.machinecoding.MovieTicketBooking.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Cinema {

    UUID id = UUID.randomUUID();
    String name;
    String address;

}
