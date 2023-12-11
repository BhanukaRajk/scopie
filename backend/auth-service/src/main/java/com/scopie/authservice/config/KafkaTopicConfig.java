package com.scopie.authservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    // FROM CUSTOMER SIDE
    @Bean
    public NewTopic newSeatReservation() {
        return TopicBuilder.name("NewSeatReserve").build();
    }

    @Bean
    public NewTopic newReservationsTopic() {
        return TopicBuilder.name("NewReservations").build();
    }

    @Bean
    public NewTopic newReservationRemoveTopic() {
        return TopicBuilder.name("NewReserveCancellation").build();
    }


    // FROM CINEMA SIDE
    @Bean
    public NewTopic newMovieTopic() {
        return TopicBuilder.name("MovieAdd").build();
    }

    @Bean
    public NewTopic newMovieTimeTopic() {
        return TopicBuilder.name("MovieTimeAdd").build();
    }

    @Bean
    public NewTopic newCinemaTopic() {
        return TopicBuilder.name("CinemaAdd").build();
    }

    @Bean
    public NewTopic newSeatAdditionTopic() {
        return TopicBuilder.name("SeatAdd").build();
    }

}
