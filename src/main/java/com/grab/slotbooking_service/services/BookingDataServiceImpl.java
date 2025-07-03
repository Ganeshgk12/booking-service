package com.grab.slotbooking_service.services;

import com.grab.slotbooking_service.models.Booking;
import com.grab.slotbooking_service.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class BookingDataServiceImpl implements BookingDataService {

    @Autowired
    BookingRepository bookingRepository;

    @Override
    public ResponseEntity<Boolean> validateUserTodayBooking(Long userId) {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay(); // 00:00:00
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX); // 23:59:59.999999999
        List<Booking> todayBookings = bookingRepository.findByCreatedAtBetween(startOfDay, endOfDay);

        if (todayBookings.isEmpty()) {
            return ResponseEntity.ok(true);
        }
        for (Booking booking : todayBookings) {
            if (booking.getUserId().equals(userId)) {      // TODO: NEED TO CHECK THE BOOKING IS COMPLETED OR NOT
                return ResponseEntity.ok(false);
            }
        }
        return ResponseEntity.ok(true);
    }
}
