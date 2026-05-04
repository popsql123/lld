package com.machinecoding.MovieTicketBooking.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("test")
    public String checkHealth(){
        return "Application is running";
    }
}
