package com.grab.slotbooking_service.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-SERVICE")
public interface UserService {

    @GetMapping("/api-user/check-user/{userId}")
    ResponseEntity<Boolean> validateUser(@PathVariable("userId") Long userId);
}
