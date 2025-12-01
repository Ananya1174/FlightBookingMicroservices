package com.bookingservice.kafka;

import com.bookingservice.event.BookingEventDto;
import com.bookingservice.config.KafkaConfig; // if you want constants or use literal
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class BookingEventListener {

    private static final Logger log = LoggerFactory.getLogger(BookingEventListener.class);

    @KafkaListener(topics = "booking-events", groupId = "flight-service-group")
    public void onBookingEvent(BookingEventDto event) {
        
        log.info("Received booking event: type={} pnr={} flightId={} user={}",
                event.getEventType(), event.getPnr(), event.getFlightId(), event.getUserEmail());

    }
}