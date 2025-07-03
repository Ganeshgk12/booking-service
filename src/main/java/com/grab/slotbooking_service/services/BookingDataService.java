package com.grab.slotbooking_service.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface BookingDataService {
    ResponseEntity<Boolean> validateUserTodayBooking(Long userId);
}
