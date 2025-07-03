package com.grab.slotbooking_service.controllers;

import com.grab.slotbooking_service.services.BookingDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api-booking/data")
public class BookingDataController {

    @Autowired
    private BookingDataService bookingDataService;

    @GetMapping("/checkuser-todaybooking")
    ResponseEntity<Boolean> checkUserTodayBooking(@RequestParam Long userId){
        return bookingDataService.validateUserTodayBooking(userId);
    }
}
