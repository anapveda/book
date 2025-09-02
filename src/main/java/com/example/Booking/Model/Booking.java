package com.example.Booking.Model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    private String bookingId;   // generated UUID

    private Long userId;
    private Long arenaId;
    private Long courtId;
    private String date;
    private String timeSlot;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;
}