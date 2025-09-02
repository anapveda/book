package com.example.Booking.Service;

import com.example.Booking.Model.BookingRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BookingProducer {

    private final KafkaTemplate<String, BookingRequest> kafkaTemplate;

    @Value("${topic.booking-requests}")
    private String bookingRequestsTopic;

    public BookingProducer(KafkaTemplate<String, BookingRequest> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendBookingRequest(BookingRequest request) {
        kafkaTemplate.send(bookingRequestsTopic, request.getBookingId(), request);
        System.out.println("📤 BookingService sent request: " + request);
    }
}
