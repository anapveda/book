package com.example.Booking.Service;


import com.example.Booking.Model.Booking;
import com.example.Booking.Model.BookingRequest;
import com.example.Booking.Model.BookingStatus;
import com.example.Booking.Repository.BookingRepository;
import com.example.Booking.Utils.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    private HttpServletRequest request;

    private final BookingProducer bookingProducer;


    public BookingService(BookingProducer bookingProducer) {
        this.bookingProducer = bookingProducer;
    }


    public Booking createBooking(BookingRequest request) {
        String authHeader = this.request.getHeader("Authorization");
        String userName = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwtToken = authHeader.substring(7);
            userName = jwtUtil.extractUsername(jwtToken);  // Pass the token
        }
        String bookingId = UUID.randomUUID().toString();

        Booking booking = new Booking();
        booking.setBookingId(bookingId);
        booking.setUserName(userName);
        booking.setArenaId(request.getArenaId());
        booking.setCourtId(request.getCourtId());
        booking.setDate(request.getDate());
        booking.setTimeSlot(request.getTimeSlot());
        booking.setStatus(BookingStatus.PENDING);

        bookingRepository.save(booking);

        request.setBookingId(bookingId); // inject generated ID
        bookingProducer.sendBookingRequest(request);

        return booking;
    }
}
