package com.grab.slotbooking_service.repositories;

import com.grab.slotbooking_service.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    boolean existsByUserId(Long userId);

    List<Booking> findAllByUserId(Long userId);

    List<Booking> findByCreatedAtBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);
}
