package com.example.Booking.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository<Bookings> extends JpaRepository<BookingRequest,Long> {
}
