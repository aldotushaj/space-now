package com.parkingtracker.service;

import com.parkingtracker.model.Parking;
import com.parkingtracker.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class ParkingService {
    //repository declaration
    private final ParkingRepository parkingRepository;
    //constructor for repository
    @Autowired
    public ParkingService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    //methods for parkings
    public List<Parking> getAllParkings() {
        return parkingRepository.findAll();
    }
    public Parking getParkingById(Long id) {
        return parkingRepository.findById(id).orElse(null);
    }
    public Parking getParkingByName(String name) {
        return parkingRepository.findByName(name);
    }
    public Parking createParking (Parking parking) {
        return parkingRepository.save(parking);
    }
    public void deleteParking(Long id){
        parkingRepository.deleteById(id);
    }

    public List<Parking> getParkingsWithAvailableSpots() {
        return parkingRepository.findByAvailableSpotsGreaterThan(0);
    }


    public void updateAvailableSpots(Long parkingId, int newAvailableSpots) {
        Parking parking = parkingRepository.findById(parkingId)
                .orElseThrow(() -> new RuntimeException("Parking not found with ID: " + parkingId));

        parking.setAvailableSpots(newAvailableSpots);
        parkingRepository.save(parking);
    }
    public List<Parking> searchParkingsByLocation(String location) {
        return parkingRepository.findByLocationIgnoreCase(location);
    }


    //Update available spots every 10 min
    @Scheduled(fixedRate = 600000) // 10 minutes (600,000 ms)
    public void updateAvailableSpotsRandomly() {
        List<Parking> parkings = parkingRepository.findAll();
        Random random = new Random();

        for (Parking parking : parkings) {
            int randomAvailableSpots = random.nextInt(parking.getTotalSpots() + 1); // Random spots between 0 and totalSpots
            parking.setAvailableSpots(randomAvailableSpots);
            parkingRepository.save(parking);
        }
    }







}
