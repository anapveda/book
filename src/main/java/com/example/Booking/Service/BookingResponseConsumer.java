package com.example.Booking.Service;

import com.example.Booking.Model.Booking;
import com.example.Booking.Repository.BookingRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class BookingResponseConsumer {
    private final BookingRepository bookingRepository;

    public BookingResponseConsumer(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @KafkaListener(topics = "${topic.booking-responses}", groupId = "booking-service", containerFactory = "bookingResponseListenerFactory")
    public void consumeBookingResponse(BookingResponse response) {
        System.out.println("📥 BookingService got response: " + response);

        Optional<Booking> bookingOpt = bookingRepository.findById(response.getBookingId());
        if (bookingOpt.isPresent()) {
            Booking booking = bookingOpt.get();
            booking.setStatus(response.getStatus());
            bookingRepository.save(booking);
            System.out.println("✅ Booking updated in DB: " + booking);
        }
    }
}
