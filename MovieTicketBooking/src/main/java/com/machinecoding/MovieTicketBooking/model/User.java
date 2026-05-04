package com.machinecoding.MovieTicketBooking.model;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class User {

    UUID id = UUID.randomUUID();
    String name;
    String address;
    Instant createdAt;

}
