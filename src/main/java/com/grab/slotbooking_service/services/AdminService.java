package com.grab.slotbooking_service.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {
    ResponseEntity<String> createDailySlot();

}
