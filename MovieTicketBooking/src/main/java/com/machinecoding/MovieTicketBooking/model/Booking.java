package com.machinecoding.MovieTicketBooking.model;

import com.machinecoding.MovieTicketBooking.enums.PaymentStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class Booking {

    UUID id = UUID.randomUUID();
    UUID userId;
    UUID showSeatId;
    PaymentStatus paymentStatus;
    BookigStatus bookingStatus;

}
