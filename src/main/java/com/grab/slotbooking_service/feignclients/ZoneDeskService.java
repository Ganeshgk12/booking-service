package com.grab.slotbooking_service.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ZONE-DESK-SERVICE")
public interface ZoneDeskService {
    @GetMapping("api/data/getdesknumber/{deskId}")
    String getDeskNumber(@PathVariable("deskId") Long deskId);

    @GetMapping("api/data/check-desk/{deskId}")
    ResponseEntity<Boolean> checkDesk(@PathVariable("deskId") Long deskId);

    @PostMapping("api/data/changeDeskStatus")
    ResponseEntity<String> changeDeskStatus(@RequestParam("deskId") Long deskId, @RequestParam String status);
}
