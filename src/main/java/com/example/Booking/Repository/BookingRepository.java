package com.example.Booking.Repository;

import com.example.Booking.Model.BookingRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository<Bookings> extends JpaRepository<BookingRequest,String> {
}
