package com.parkingtracker.controller;

import com.parkingtracker.model.Parking;
import com.parkingtracker.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ParkingController {

    @Autowired
    private ParkingService parkingService;

    // Home page - shows all parkings (requires authentication)
    @GetMapping("/")
    public String showAllParkings(@RequestParam(required = false) String location, Model model) {
        List<Parking> parkings;

        if (location != null && !location.trim().isEmpty()) {
            // Search parkings by location
            parkings = parkingService.searchParkingsByLocation(location);
        } else {
            // Get all parkings
            parkings = parkingService.getAllParkings();
        }

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

    // ADMIN FUNCTIONALITY

    // Show admin dashboard
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminDashboard(Model model) {
        List<Parking> parkings = parkingService.getAllParkings();
        model.addAttribute("parkings", parkings);
        return "admin/dashboard";
    }
    

    // Show form to create new parking
    @GetMapping("/admin/parkings/create")
    @PreAuthorize("hasRole('ADMIN')")
    public String showCreateForm(Model model) {
        Parking parking = new Parking();
        parking.setTotalSpots(1);
        model.addAttribute("parking", parking);
        return "admin/parking-form";
    }

    // Process parking creation
    @PostMapping("/admin/parkings/create")
    @PreAuthorize("hasRole('ADMIN')")
    public String createParking(@ModelAttribute Parking parking, RedirectAttributes redirectAttributes) {
        parkingService.createParking(parking);
        redirectAttributes.addFlashAttribute("successMessage", "Parking created successfully!");
        return "redirect:/admin";
    }

    // Show form to edit parking
    @GetMapping("/admin/parkings/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String showEditForm(@PathVariable Long id, Model model) {
        Parking parking = parkingService.getParkingById(id);
        if (parking != null) {
            model.addAttribute("parking", parking);
            return "admin/parking-form";
        } else {
            return "redirect:/admin";
        }
    }

    // Process parking update
    @PostMapping("/admin/parkings/update")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateParking(@ModelAttribute Parking parking, RedirectAttributes redirectAttributes) {
        parkingService.createParking(parking); // save method works for both create and update
        redirectAttributes.addFlashAttribute("successMessage", "Parking updated successfully!");
        return "redirect:/admin";
    }

    // Delete parking
    @GetMapping("/admin/parkings/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteParking(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        parkingService.deleteParking(id);
        redirectAttributes.addFlashAttribute("successMessage", "Parking deleted successfully!");
        return "redirect:/admin";
    }
}