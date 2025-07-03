package com.grab.slotbooking_service.feignclients;

import jakarta.servlet.http.HttpSession;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("VALIDATION-SERVICE")
public interface BookingValidationService {

    @GetMapping("/api-booking/session/check-session")
    ResponseEntity<Boolean> checkSession(HttpSession session);

    @PostMapping("//api-booking/session/sessionStatusChange")
    ResponseEntity<String> sessionStatusChange(@RequestParam String sessionId, String status);

}
