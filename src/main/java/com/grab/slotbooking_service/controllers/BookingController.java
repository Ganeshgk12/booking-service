package com.grab.slotbooking_service.controllers;

import com.grab.slotbooking_service.models.Booking;
import com.grab.slotbooking_service.services.BookingService;
import com.grab.slotbooking_service.wrappers.BookingRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api-booking/slot")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/todayBookings")
    public ResponseEntity<List<Booking>> getTodayBookings(){
        return bookingService.getAllTodayBookings();
    }

    @PostMapping("/cancelBooking")
    public ResponseEntity<String> cancelBooking(@RequestParam Long bookingId) {
        return bookingService.cancelBooking(bookingId);
    }

    @PostMapping("/confirmBooking")
    public ResponseEntity<String> confirmBooking(@RequestBody BookingRequest bookingRequest, @RequestParam Long reservationId) {
        return bookingService.confirmBooking(bookingRequest, reservationId);
    }

}
