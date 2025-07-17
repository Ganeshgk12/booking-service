package com.grab.slotbooking_service.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "RESERVATION-SERVICE")
public interface ReservationService {

    @GetMapping("/api/reservations/check-reservation-id")
    ResponseEntity<Boolean> checkReservationId(@RequestParam Long reservationId);

    @GetMapping("/api/reservations/check-reservation-status")
    ResponseEntity<Boolean> checkReservationStatus(@RequestParam Long reservationId);

    @PutMapping("/api/reservations/change-reservation-status")
    ResponseEntity<String> changeReservationStatus(@RequestParam Long reservationId, @RequestParam String status);
}
