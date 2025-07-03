package com.grab.slotbooking_service.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "attendances")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attendanceId;

    @Enumerated(EnumType.STRING)
    private Message message;
    private Long userId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public enum Message{
        SUCCESS, FAIL, PENDING
    }
}
