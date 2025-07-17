package com.grab.slotbooking_service.services.Implementations;

import com.grab.slotbooking_service.feignclients.ReservationService;
import com.grab.slotbooking_service.feignclients.UserService;
import com.grab.slotbooking_service.feignclients.ZoneDeskService;
import com.grab.slotbooking_service.models.Booking;
import com.grab.slotbooking_service.models.BookingStatus;
import com.grab.slotbooking_service.repositories.BookingRepository;
import com.grab.slotbooking_service.services.BookingService;
import com.grab.slotbooking_service.wrappers.BookingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    private BookingRequest bookingRequest = new BookingRequest();

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ZoneDeskService zoneDeskService;

    @Autowired
    private UserService userService;


    @Override
    public ResponseEntity<List<Booking>> userAllBookings(Long userId) {
        if (bookingRepository.existsByUserId(userId)) {
            List<Booking> bookingList = bookingRepository.findAllByUserId(userId);
            return ResponseEntity.ok(bookingList);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Booking> getBookingById(Long id) {
        if (bookingRepository.existsById(id)) {
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

    @Override
    public ResponseEntity<String> cancelBooking(Long bookingId) {
        if (bookingRepository.existsById(bookingId)) {
            Optional<Booking> booking = bookingRepository.findById(bookingId);
            if (booking.isPresent()) {
                if (booking.get().getStatus() == BookingStatus.BOOKED) {
                    zoneDeskService.changeDeskStatus(booking.get().getDeskId(), "AVAILABLE");
                    booking.get().setStatus(BookingStatus.CANCELLED);
                    bookingRepository.save(booking.get());
                    return ResponseEntity.ok("Your booking is cancelled successfully.");
                }
                return ResponseEntity.badRequest().body("Your booking is already cancelled or expired please try again");
            }
        }
        return ResponseEntity.badRequest().body("Your Booking not found with this id " + bookingId + " please try again");
    }

    @Override
    public ResponseEntity<String> confirmBooking(BookingRequest bookingRequest, Long reservationId) {
        if (bookingRequest != null && bookingRequest.getUserId() != null && bookingRequest.getDeskId() != null && reservationId != null) {
            if (isValidResponse(userService.validateUser(bookingRequest.getUserId()))
                    && isValidResponse(zoneDeskService.checkDesk(bookingRequest.getDeskId()))) {

                if (isValidResponse(reservationService.checkReservationId(reservationId))
                        && isValidResponse(reservationService.checkReservationStatus(reservationId))) {

                    String deskNumber = zoneDeskService.getDeskNumber(bookingRequest.getDeskId());
                    String bookingNumber = bookingRequest.generateBookingNumber(deskNumber);
                    Booking booking = bookingRequest.buildBooking(bookingRequest, bookingNumber);
                    bookingRepository.save(booking);
                    zoneDeskService.changeDeskStatus(bookingRequest.getDeskId(), "BOOKED");

                    reservationService.changeReservationStatus(reservationId, "BOOKED");

                    return ResponseEntity.ok("Your booking is successfully added");
                }
                return ResponseEntity.badRequest().body("Booking session is not available please try again");
            }
            return ResponseEntity.badRequest().body("User Id or Desk Id is not valid please try again with valid details");
        }
        return ResponseEntity.badRequest().body("User Id or Desk Id is not valid please try again with valid details");
    }

    private static boolean isValidResponse(ResponseEntity<?> response) {
        return response != null && response.getStatusCode().is2xxSuccessful() && Boolean.TRUE.equals(response.getBody());
    }

}
