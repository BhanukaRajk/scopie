package com.scopie.authservice.kafka;

import org.springframework.stereotype.Component;
import org.springframework.kafka.annotation.KafkaListener;

@Component
public class KafkaListeners {

    @KafkaListener(
            topics = "movie_details",
            groupId = "groupId"
    )
    public void listener(String message) {
        System.out.println("Message received:" + message);
    }
}
