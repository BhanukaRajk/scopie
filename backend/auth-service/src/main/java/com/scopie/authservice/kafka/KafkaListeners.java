package com.scopie.authservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.scopie.authservice.kafka.dto.KafkaCinemaDTO;
import com.scopie.authservice.kafka.dto.KafkaMovieDTO;
import com.scopie.authservice.kafka.dto.KafkaMovieTimeDTO;
import com.scopie.authservice.kafka.dto.KafkaReservationDTO;
import com.scopie.authservice.service.KafkaService;
import com.scopie.authservice.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.List;

@Component
public class KafkaListeners {

    @Autowired
    private KafkaService kafkaService;

    @Autowired
    private MovieService movieService;

    private final ObjectMapper objMapper = new ObjectMapper();

    // GET THE KAFKA MESSAGE WHEN CINEMA SIDE MAKE CHANGES ON ITS DETAILS
    @KafkaListener( topics = "CinemaUpdate")
    public void cinemaUpdater(String payload) {
        System.out.println("Received raw payload: " + payload); // TODO: REMOVE THIS
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            KafkaMovieTimeDTO kfkMovieTimeDTO = objectMapper.readValue(payload, KafkaMovieTimeDTO.class);

            // UPDATE THE CINEMA DATA
            kafkaService.updateCinema(kfkCinemaDTO);
            System.out.println("Deserialized CinemaDTO: " + kfkCinemaDTO); // TODO: REMOVE THIS
        } catch (Exception e) {
            System.out.println("Error deserializing CinemaDTO: " + e.getMessage()); // TODO: REMOVE THIS
        }
    }




    // TODO: THIS IS ONLY FOR TESTING PURPOSE
    @KafkaListener( topics = "test_message", groupId = "groupId" )
    public void listener(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("Message received: " + objectMapper.readValue(message, KafkaReservationDTO.class));

        ModelMapper modelMapper = new ModelMapper();
        System.out.println("Message received: " + modelMapper.map(message, KafkaReservationDTO.class));
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
    @KafkaListener( topics = "CinemaAdd", groupId = "groupId" )
    public void updateCinema(String payload) {
        System.out.println("Received raw payload: " + payload); // TODO: REMOVE THIS
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            KafkaCinemaDTO kfkCinemaDTO = objectMapper.readValue(payload, KafkaCinemaDTO.class);

            // UPDATE THE CINEMA DATA
            kafkaService.updateCinema(kfkCinemaDTO);
            System.out.println("Deserialized Cinema DTO: " + kfkCinemaDTO); // TODO: REMOVE THIS
        } catch (Exception e) {
            System.out.println("Error deserializing Cinema DTO: " + e.getMessage()); // TODO: REMOVE THIS
        }
    }

    @KafkaListener(topics = "MovieAdd", groupId = "groupId")
    public void updateMovie(String payload) {
        System.out.println("Received raw payload: " + payload); // TODO: REMOVE THIS
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            KafkaMovieDTO kfkMovieDTO = objectMapper.readValue(payload, KafkaMovieDTO.class);

            // UPDATE THE MOVIE DATA
            movieService.updateMovieList(kfkMovieDTO);
            System.out.println("Deserialized MovieDto: " + kfkMovieDTO); // TODO: REMOVE THIS
        } catch (Exception e) {
            System.out.println("Error deserializing MovieDto: " + e.getMessage()); // TODO: REMOVE THIS
        }
    }

}
