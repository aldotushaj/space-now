package com.parkingtracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling

public class ParkingTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkingTrackerApplication.class, args);
    }
}
