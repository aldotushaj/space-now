package com.parkingtracker.repository;

import com.parkingtracker.model.Parking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, Long> {
    public Parking findByName(String name);
    List<Parking> findByAvailableSpotsGreaterThan(int spots);
    List<Parking> findByLocationIgnoreCase(String location);


}
