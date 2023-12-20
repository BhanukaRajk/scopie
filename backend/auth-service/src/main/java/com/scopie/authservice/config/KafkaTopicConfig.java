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
        return TopicBuilder.name("New_Reserved_Seat_Topic").build();
    }

    @Bean
    public NewTopic newReservationsTopic() {
        return TopicBuilder.name("New_Reservation_Topic").build();
    }

    @Bean
    public NewTopic newReservationRemoveTopic() {
        return TopicBuilder.name("New_Reserve_Cancellation_Topic").build();
    }

    // FROM CINEMA SIDE
    @Bean
    public NewTopic newMovieTopic() {
        return TopicBuilder.name("New_Movie_Topic").build();
    }

    @Bean
    public NewTopic newMovieTimeTopic() {
        return TopicBuilder.name("New_Movie_Time_Topic").build();
    }

    @Bean
    public NewTopic newCinemaTopic() {
        return TopicBuilder.name("New_Cinema_Topic").build();
    }

    @Bean
    public NewTopic newSeatAdditionTopic() {
        return TopicBuilder.name("New_Seat_Add_Topic").build();
    }

}
