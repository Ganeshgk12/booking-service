package com.grab.slotbooking_service.services;

import com.grab.slotbooking_service.feignclients.BookingValidationService;
import com.grab.slotbooking_service.feignclients.UserService;
import com.grab.slotbooking_service.feignclients.ZoneDeskService;
import com.grab.slotbooking_service.models.Booking;
import com.grab.slotbooking_service.repositories.BookingRepository;
import com.grab.slotbooking_service.wrappers.BookingRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    private BookingRequest bookingRequest = new BookingRequest();

    @Autowired
    private ZoneDeskService zoneDeskService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookingValidationService bookingValidationService;

    @Override
    public ResponseEntity<String> addBooking(BookingRequest bookingRequest, HttpSession session) {


        String s = session.getId();

        if (bookingValidationService.checkSession(session) == ResponseEntity.ok().body(true)) {
            if (userService.validateUser(bookingRequest.getUserId()) == ResponseEntity.ok().body(false)) {
                zoneDeskService.changeDeskStatus(bookingRequest.getDeskId(), "AVAILABLE");
                session.invalidate();
                bookingValidationService.sessionStatusChange(s, "DESTROYED");
                return ResponseEntity.badRequest().body("Invalid user");
            }
            if (zoneDeskService.checkDesk(bookingRequest.getDeskId()) == ResponseEntity.ok().body(false)) {
                zoneDeskService.changeDeskStatus(bookingRequest.getDeskId(), "AVAILABLE");
                session.invalidate();
                bookingValidationService.sessionStatusChange(s, "DESTROYED");
                return ResponseEntity.badRequest().body("Invalid desk");
            }
            String uniqueNumber = zoneDeskService.getDeskNumber(bookingRequest.getDeskId());
            String newNumber = bookingRequest.generateBookingNumber(uniqueNumber);


            zoneDeskService.changeDeskStatus(bookingRequest.getDeskId(), "BOOKED");
            Booking booking = bookingRequest.buildBooking(bookingRequest, newNumber);
            bookingRepository.save(booking);
            bookingValidationService.sessionStatusChange(s, "COMPLETED");
            session.invalidate();

            return ResponseEntity.ok(booking.getBookingNumber());

        }
        zoneDeskService.changeDeskStatus(bookingRequest.getDeskId(), "AVAILABLE");
        session.invalidate();
        bookingValidationService.sessionStatusChange(s, "DESTROYED");
        return ResponseEntity.badRequest().body("Your session expired or invalid please try again");
    }

    @Override
    public ResponseEntity<List<Booking>> userAllBookings(Long userId) {
        if (bookingRepository.existsByUserId(userId)){
            List<Booking> bookingList = bookingRepository.findAllByUserId(userId);
            return ResponseEntity.ok(bookingList);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Booking> getBookingById(Long id) {
        if (bookingRepository.existsById(id)){
            Booking booking = bookingRepository.findById(id).orElse(null);
            return ResponseEntity.ok(booking);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<List<Booking>> getAllTodayBookings() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay(); // 00:00:00
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX); // 23:59:59.999999999
        List<Booking> todayBookings = bookingRepository.findByCreatedAtBetween(startOfDay, endOfDay);
        return ResponseEntity.ok(todayBookings);
    }
}
