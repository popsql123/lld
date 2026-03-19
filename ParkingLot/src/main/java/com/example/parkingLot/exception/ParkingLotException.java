package com.example.parkingLot.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ParkingLotException extends RuntimeException {

    private final HttpStatus status;
    private final int errorCode;

    public ParkingLotException(HttpStatus status, int errorCode, String message){
        super(message);
        this.status = status;
        this.errorCode = errorCode;
    }
}
