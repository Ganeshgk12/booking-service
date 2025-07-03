package com.grab.slotbooking_service.controllers;

import com.grab.slotbooking_service.models.Booking;
import com.grab.slotbooking_service.services.BookingService;
import com.grab.slotbooking_service.wrappers.BookingRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api-booking/slot")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/booking")
    public ResponseEntity<String> createBooking(@RequestBody BookingRequest bookingRequest, HttpSession session) {
        return bookingService.addBooking(bookingRequest, session);
    }

    @GetMapping("/todayBookings")
    public ResponseEntity<List<Booking>> getTodayBookings(){
        return bookingService.getAllTodayBookings();
    }
}
