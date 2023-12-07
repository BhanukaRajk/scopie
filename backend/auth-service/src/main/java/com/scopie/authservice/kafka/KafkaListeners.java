package com.scopie.authservice.kafka;

import com.scopie.authservice.dto.MovieDTO;
import com.scopie.authservice.entity.Movie;
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

    @KafkaListener( topics = "test", groupId = "groupId" )
    public void listener(String message) {
        System.out.println("Message received: " + message);
    }

    // GET CURRENT WHEN CUSTOMER NEED THE CURRENT SEAT BOOKING DETAILS
    @KafkaListener( topics = "seat_details", groupId = "groupId" )
    public List<Boolean> seatAvailability(List<Boolean> seatBookings)  {
        return seatBookings;
    }

    // LISTENER TO LISTEN WHEN ANY MOVIE IS ADDED OR UPDATED
    @KafkaListener( topics = "movie_details", groupId = "groupId" )
    public void movieUpdater(Movie movie) {
        movieService.updateMovieList(movie);
        System.out.println("Message received: " + movie.toString());
    }

    // GET MOVIE DATA FROM OTHER END
    @KafkaListener(topics = "MovieAdd", groupId = "groupId")
    void updateMovie(String payload) {
        System.out.println("Received raw payload: " + payload);

        try {
            // Attempt to manually deserialize the JSON string to MovieDto
            ObjectMapper objectMapper = new ObjectMapper();
            MovieDTO movieDto = objectMapper.readValue(payload, MovieDTO.class);
            System.out.println("Deserialized MovieDto: " + movieDto);
        } catch (Exception e) {
            System.out.println("Error deserializing MovieDto: " + e.getMessage());
        }
    }

}
