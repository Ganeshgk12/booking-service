package com.grab.slotbooking_service.services;

import com.grab.slotbooking_service.models.Booking;
import com.grab.slotbooking_service.wrappers.BookingRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface BookingService {

    ResponseEntity<String> addBooking(BookingRequest bookingRequest, HttpSession session);
    ResponseEntity<List<Booking>> userAllBookings(Long userId);
    ResponseEntity<Booking> getBookingById(Long id);
    ResponseEntity<List<Booking>> getAllTodayBookings();
}
