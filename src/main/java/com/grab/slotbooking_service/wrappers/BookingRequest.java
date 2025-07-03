package com.grab.slotbooking_service.wrappers;

import com.grab.slotbooking_service.feignclients.ZoneDeskService;
import com.grab.slotbooking_service.models.Booking;
import com.grab.slotbooking_service.models.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class BookingRequest {
    private Long userId;
    private Long deskId;


    public String generateBookingNumber(String uniqueNum) {
        // TODO: DESK NUMBER ex:(B1-F1-Z1-D1)
        // TODO: CONCAT LIKE THIS -> B1-F1-Z1-D1-(STATUS OF BOOKING)
        return uniqueNum + " - " + " BOOKED";
    }

    public Booking buildBooking(BookingRequest bookingRequest, String uniqueNumber) {
        return new Booking(uniqueNumber, bookingRequest.getUserId(), bookingRequest.getDeskId(), BookingStatus.BOOKED);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDeskId() {
        return deskId;
    }

    public void setDeskId(Long deskId) {
        this.deskId = deskId;
    }
}
