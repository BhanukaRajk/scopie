package com.scopie.authservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.scopie.authservice.kafka.dto.KafkaMovieDTO;
import com.scopie.authservice.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.List;

@Component
public class KafkaListeners {

    @Autowired
    private MovieService movieService;

    private final ObjectMapper objMapper = new ObjectMapper();

    @KafkaListener( topics = "CinemaUpdate")
    public void cinemaUpdater(String payload) {}

    @KafkaListener( topics = "MovieUpdate")
    public void movieUpdater(String payload) {}





    @KafkaListener( topics = "test", groupId = "groupId" )
    public void listener(String message) {
        System.out.println("Message received: " + message);
    }

    // GET CURRENT WHEN CUSTOMER NEED THE CURRENT SEAT BOOKING DETAILS
    @KafkaListener( topics = "seat_details", groupId = "groupId" )
    public List<Boolean> seatAvailability(String payload)  {
        System.out.println("Seat reservation data received!" + payload);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(payload, new TypeReference<List<Boolean>>() {}); // SEND THE SEAT RESERVATION DATA USING BOOLEAN ARRAY
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    // LISTENER TO LISTEN WHEN ANY MOVIE IS ADDED OR UPDATED
    @KafkaListener( topics = "MyReservations", groupId = "groupId" )
    public void movieUpdate(String payload) {
        System.out.println("Received raw payload: " + payload);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            KafkaMovieDTO movieDto = objectMapper.readValue(payload, KafkaMovieDTO.class);

            // UPDATE THE MOVIE DATA
            movieService.updateMovieList(movieDto);
            System.out.println("Deserialized MovieDto: " + movieDto);
        } catch (Exception e) {
            System.out.println("Error deserializing MovieDto: " + e.getMessage());
        }
    }

    // GET MOVIE DATA FROM OTHER END
//    @KafkaListener(topics = "MovieAdd", groupId = "groupId")
//    void updateMovie(String payload) {
//        System.out.println("Received raw payload: " + payload);
//
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            MovieDTO movieDto = objectMapper.readValue(payload, MovieDTO.class);
//            System.out.println("Deserialized MovieDto: " + movieDto);
//        } catch (Exception e) {
//            System.out.println("Error deserializing MovieDto: " + e.getMessage());
//        }
//    }

    @KafkaListener(topics = "MovieAdd", groupId = "groupId")
    public void updateMovie(String payload) {
        System.out.println("Received raw payload: " + payload);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            KafkaMovieDTO movieDto = objectMapper.readValue(payload, KafkaMovieDTO.class);

            // UPDATE THE MOVIE DATA
            movieService.updateMovieList(movieDto);
            System.out.println("Deserialized MovieDto: " + movieDto);
        } catch (Exception e) {
            System.out.println("Error deserializing MovieDto: " + e.getMessage());
        }
    }

}
