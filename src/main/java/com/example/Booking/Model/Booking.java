package com.example.Booking.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "booking")
public class Booking {
    @Id
    private String bookingId;   // generated UUID
    private String userName;
    private Long arenaId;
    private Long courtId;
    private String date;
    private String timeSlot;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;
}