package com.grab.slotbooking_service.services;

import com.grab.slotbooking_service.models.Booking;
import com.grab.slotbooking_service.wrappers.BookingRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface BookingService {

    ResponseEntity<List<Booking>> userAllBookings(Long userId);
    ResponseEntity<Booking> getBookingById(Long id);
    ResponseEntity<List<Booking>> getAllTodayBookings();

    ResponseEntity<String> cancelBooking(Long bookingId);

    ResponseEntity<String> confirmBooking(BookingRequest bookingRequest, Long reservationId);
}
