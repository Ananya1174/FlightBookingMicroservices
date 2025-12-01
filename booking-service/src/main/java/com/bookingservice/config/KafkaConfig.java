package com.bookingservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    public static final String TOPIC_BOOKING_EVENTS = "booking-events";

    @Bean
    public NewTopic bookingEventsTopic() {
        return new NewTopic(TOPIC_BOOKING_EVENTS, 1, (short) 1);
    }
}