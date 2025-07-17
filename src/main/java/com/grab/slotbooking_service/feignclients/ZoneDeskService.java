package com.grab.slotbooking_service.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ZONE-DESK-SERVICE")
public interface ZoneDeskService {
    @GetMapping("api/data/getdesknumber/{deskId}")
    String getDeskNumber(@PathVariable("deskId") Long deskId);

    @GetMapping("api/data/check-desk/{deskId}")
    ResponseEntity<Boolean> checkDesk(@PathVariable("deskId") Long deskId);

    @PutMapping("api/data/changeDeskStatus")
    ResponseEntity<String> changeDeskStatus(@RequestParam("deskId") Long deskId, @RequestParam String status);

    @PutMapping("api/data/change-all-desk-status-available")
    ResponseEntity<String> changeAllDeskStatusAvailable();
}
