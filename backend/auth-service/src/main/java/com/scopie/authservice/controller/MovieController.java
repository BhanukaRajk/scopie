package com.scopie.authservice.controller;

import com.scopie.authservice.component.JwtGenerator;
import com.scopie.authservice.dto.MovieDTO;
import com.scopie.authservice.dto.MovieShowsDTO;
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
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*")
@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    KafkaTemplate<String, Long> kafkaTemplate;

    @Autowired
    private JwtGenerator jwtGenerator;

    // GET AND SEND ALL MOVIES TO THE MOVIES PAGE WITH FILTERS
    @GetMapping(value = "")
    public List<MovieDTO> getMovies(@Nullable @RequestParam String filter, @RequestHeader(name = "Authorization", required = true) String authorizationHeader) {
        if((jwtGenerator.validateToken(authorizationHeader.split(" ")[1]))) {
            if (filter != null) {
                return movieService.getMovies(filter);
            } else {
                return movieService.getMovies(false);
            }
        } else {
            throw new RuntimeException("Unauthorized user!");
        }
    }

    @GetMapping(value = "/recent")
    public List<MovieDTO> getRecentMovies(@Nullable @RequestParam String filter, @RequestHeader(name = "Authorization", required = true) String authorizationHeader) {
        if((jwtGenerator.validateToken(authorizationHeader.split(" ")[1]))) {
            if (filter != null) {
                return movieService.getMovies(filter);
            } else {
                return movieService.getMovies(true);
            }
        } else {
            throw new RuntimeException("Unauthorized user!");
        }
    }

//    @GetMapping(value = "")
//    public ResponseEntity<Map<String, List<MovieDTO>>> getMovies(@Nullable @RequestParam String filter, @RequestHeader(name = "Authorization", required = true) String authorizationHeader) {
//        if((jwtGenerator.validateToken(authorizationHeader.split(" ")[1]))) {
//            try {
//                if (filter != null) {
//                    return ResponseEntity.status(HttpStatus.OK).body(Map.of("data", movieService.getMovies(filter)));
//                } else {
//                    return ResponseEntity.status(HttpStatus.OK).body(Map.of("data", movieService.getMovies(false)));
//                }
//            } catch (Exception e) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//            }
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
//        }
//    }


//    @GetMapping(value = "/recent")
//    public ResponseEntity<Map<String, List<MovieDTO>>> getRecentMovies(@Nullable @RequestParam String filter, @RequestHeader(name = "Authorization", required = true) String authorizationHeader) {
//        if((jwtGenerator.validateToken(authorizationHeader.split(" ")[1]))) {
//            try {
//                if (filter != null) {
//                    return ResponseEntity.status(HttpStatus.OK).body(Map.of("data", movieService.getMovies(filter)));
//                } else {
//                    return ResponseEntity.status(HttpStatus.OK).body(Map.of("data", movieService.getMovies(true)));
//                }
//            } catch (Exception e) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//            }
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
//        }
//    }


    // SEND THE SPECIFIC MOVIE DETAILS WHEN CUSTOMER CLICKS ON SOME MOVIE
    @GetMapping(value = "/")
    public ResponseEntity<MovieShowsDTO> expandSpecificMovie(@RequestParam long movieId, @RequestHeader(name = "Authorization", required = true) String authorizationHeader) {
        if((jwtGenerator.validateToken(authorizationHeader.split(" ")[1]))) {
            return ResponseEntity.status(HttpStatus.OK).body(movieService.movieDetails(movieId));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

}
