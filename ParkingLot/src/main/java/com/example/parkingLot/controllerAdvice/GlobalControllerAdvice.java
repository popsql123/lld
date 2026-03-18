package com.example.parkingLot.controllerAdvice;

import com.example.parkingLot.exception.ParkingLotException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(ParkingLotException.class)
    public ResponseEntity<String> handleParkingLotException(ParkingLotException ex){
        return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
    }
}
