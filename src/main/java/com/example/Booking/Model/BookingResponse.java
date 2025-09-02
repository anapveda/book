package com.example.Booking.Model;

import lombok.Data;

@Data
public class BookingResponse {
    private String bookingId;
    private BookingStatus status;
}