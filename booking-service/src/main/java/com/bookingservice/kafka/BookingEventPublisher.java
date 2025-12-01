package com.bookingservice.kafka;

import com.bookingservice.event.BookingEventDto;
import com.bookingservice.config.KafkaConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class BookingEventPublisher {

    private final KafkaTemplate<String, BookingEventDto> kafkaTemplate;
    private static final Logger log = LoggerFactory.getLogger(BookingEventPublisher.class);

    public BookingEventPublisher(KafkaTemplate<String, BookingEventDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Publish event (fire-and-forget but with callbacks logged).
     */
    public void publishBookingEvent(BookingEventDto event) {
        try {
            kafkaTemplate.send(KafkaConfig.TOPIC_BOOKING_EVENTS, event.getPnr(), event)
                    .addCallback(new ListenableFutureCallback<>() {
                        @Override
                        public void onFailure(Throwable ex) {
                            log.warn("Failed to publish booking event pnr={} error={}", event.getPnr(), ex.toString());
                        }

                        @Override
                        public void onSuccess(org.springframework.kafka.support.SendResult<String, BookingEventDto> result) {
                            log.info("Published booking-event pnr={} partition={} offset={}",
                                    event.getPnr(),
                                    result.getRecordMetadata().partition(),
                                    result.getRecordMetadata().offset());
                        }
                    });
        } catch (Exception e) {
            log.warn("Exception while publishing booking event pnr={} : {}", event.getPnr(), e.toString());
        }
    }
}