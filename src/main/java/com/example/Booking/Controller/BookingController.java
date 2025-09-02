package com.example.Booking.Controller;

import com.example.Booking.Model.Booking;
import com.example.Booking.Model.BookingRequest;
import com.example.Booking.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookingController {

  @Autowired
  BookingService bookingService;
    @PostMapping
    public ResponseEntity<Booking> bookCourt(@RequestBody BookingRequest request) {
     return ResponseEntity.ok(bookingService.createBooking(request));
    }


}
