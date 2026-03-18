package com.example.parkingLot.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ParkingLotException extends RuntimeException {

    private final HttpStatus status;

    public ParkingLotException(HttpStatus status, String message){
        super(message);
        this.status = status;
    }
}
