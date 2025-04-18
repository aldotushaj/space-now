package com.parkingtracker.controller;

import com.parkingtracker.model.Parking;
import com.parkingtracker.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ParkingController {

    @Autowired
    private ParkingService parkingService;

    // Home page - shows all parkings
    @GetMapping("/")
    public String showAllParkings(Model model) {
        List<Parking> parkings = parkingService.getAllParkings();
        model.addAttribute("parkings", parkings);
        return "index";  // Maps to templates/index.html
    }

    // Details page for a specific parking
    @GetMapping("/parkings/{id}")
    public String showParkingDetails(@PathVariable Long id, Model model) {
        Parking parking = parkingService.getParkingById(id);
        if (parking != null) {
            model.addAttribute("parking", parking);
            return "parking/details";  // Maps to templates/parking/details.html
        } else {
            // If parking not found, redirect to home
            return "redirect:/";
        }
    }
}