package com.example.Booking.Model;

import lombok.Data;

@Data

public class BookingRequest {
    private String bookingId;  // generated in BookingService
    private String userName;
    private Long arenaId;
    private Long courtId;
    private String date;       // e.g. 2025-09-02
    private String timeSlot;   // e.g. 10:00-11:00
}
