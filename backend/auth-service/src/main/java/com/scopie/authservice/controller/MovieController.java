package com.scopie.authservice.controller;

import com.scopie.authservice.dto.MovieDTO;
import com.scopie.authservice.entity.Movie;
import com.scopie.authservice.kafka.KafkaProducer;
import com.scopie.authservice.service.MovieService;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    KafkaTemplate<String, Integer> kafkaTemplate;

    // GET AND SEND ALL MOVIES TO THE MOVIES PAGE WITH FILTERS
    @GetMapping(value = "/")
    public List<MovieDTO> getMovies(@Nullable @RequestParam String filter) {
        if(filter != null) {
//            return movieService.getMovies(filter);
            return movieService.getMovies();

        } else {
            return movieService.getMovies();
        }
    }

    // SEND THE SPECIFIC MOVIE DETAILS WHEN CUSTOMER CLICKS ON SOME MOVIE
    @GetMapping(value = "/:id")
    public ResponseEntity<String> expandSpecificMovie(@RequestParam long movieId) {
        return ResponseEntity.status(HttpStatus.OK).body(movieService.movieDetails(movieId).toString());
    }









    // TODO: REMOVE THIS FUNCTION
    @GetMapping(value = "/test")
    public String testMovies() {
        kafkaTemplate.send("movie_details", 2);
        return "Test function working!";
    }

    @GetMapping(value = "/test2")
    public String test2Movies() {
        kafkaTemplate.send("movie_details", 2);
        return "Test 2 function working!";
    }

    @GetMapping(value = "/test3")
    public String testKafka() {
        return kafkaTemplate.toString();
    }


}
