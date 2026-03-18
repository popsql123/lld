package com.example.parkingLot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceHealthController {

    @GetMapping("/test")
    public String serviceHealth() {
        return "Parking lot service running..";
    }
}
