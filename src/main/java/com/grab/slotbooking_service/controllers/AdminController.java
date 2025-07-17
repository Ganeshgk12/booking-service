package com.grab.slotbooking_service.controllers;

import com.grab.slotbooking_service.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api-admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PutMapping("create-daily-slot")
    public ResponseEntity<String> createDailySlot() {
        return adminService.createDailySlot();
    }
}
