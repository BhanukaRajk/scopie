package com.scopie.authservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic movieTopic() {
        return TopicBuilder.name("test").build();
    }

    @Bean
    public NewTopic newMovieTopic() {
        return TopicBuilder.name("MovieAdd").build();
    }

    @Bean
    public NewTopic newReservationsTopic() {
        return TopicBuilder.name("NewReservations").build();
    }

    @Bean
    public NewTopic reservationTopic() {
        return TopicBuilder.name("reservation_details").build();
    }

}
