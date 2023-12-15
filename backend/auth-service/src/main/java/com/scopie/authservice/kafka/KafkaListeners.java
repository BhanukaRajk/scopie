package com.scopie.authservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
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

@Component
public class KafkaListeners {

    @Autowired
    private KafkaService kafkaService;

    @Autowired
    private MovieService movieService;


    // GET THE KAFKA MESSAGE WHEN CINEMA SIDE MAKE CHANGES ON ITS DETAILS
    // TODO: THIS IS ONLY FOR TESTING PURPOSE
    @KafkaListener( topics = "test_message", groupId = "groupId" )
    public void listener(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("Message received: " + objectMapper.readValue(message, KafkaReservationDTO.class));

        ModelMapper modelMapper = new ModelMapper();
        System.out.println("Message received: " + modelMapper.map(message, KafkaReservationDTO.class));
    }


    @KafkaListener( topics = "SeatAdd")
    public void seatUpdater(String payload) {
        System.out.println("Received raw payload: " + payload); // TODO: REMOVE THIS
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Long seatId = objectMapper.readValue(payload, Long.class);

            // UPDATE THE SEAT COUNT
            kafkaService.addNewSeat(seatId); // TODO: ADD NEW SEATS TO TABLE
            System.out.println("Deserialized seat number: " + seatId); // TODO: REMOVE THIS
        } catch (Exception e) {
            System.out.println("Error deserializing seat number: " + e.getMessage()); // TODO: REMOVE THIS
        }
    }

    @KafkaListener( topics = "MovieTimeAdd")
    public void movieTimeUpdater(String payload) {
        System.out.println("Received raw payload: " + payload); // TODO: REMOVE THIS
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            KafkaMovieTimeDTO kfkMovieTimeDTO = objectMapper.readValue(payload, KafkaMovieTimeDTO.class);

            // UPDATE THE CINEMA DATA
//            kafkaService.updateCinema(kfkMovieTimeDTO); // TODO: UPDATE MOVIE TIME
            System.out.println("Deserialized CinemaDTO: " + kfkMovieTimeDTO); // TODO: REMOVE THIS
        } catch (Exception e) {
            System.out.println("Error deserializing CinemaDTO: " + e.getMessage()); // TODO: REMOVE THIS
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
