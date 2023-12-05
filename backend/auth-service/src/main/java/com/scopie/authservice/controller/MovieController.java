//package com.scopie.authservice.controller;
//
//import com.scopie.authservice.entity.Movie;
//import com.scopie.authservice.service.MovieService;
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@NoArgsConstructor
//@AllArgsConstructor
//@CrossOrigin(origins = "*", allowedHeaders = "*")
//@RestController
//@RequestMapping("/api/movies")
//public class MovieController {
//
//    @Autowired
//    private MovieService movieService;
//
//    @GetMapping(value = "/")
//    public List<Movie> getMovies() {
//        return movieService.getMovies();
//    }
//
//    @GetMapping(value = "/test")
//    public String testMovies() {
//        return "Test function working!";
//    }
//
//
//}
