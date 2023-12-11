package com.scopie.authservice.service;

import com.scopie.authservice.dto.MovieDTO;
import com.scopie.authservice.entity.Movie;
import com.scopie.authservice.kafka.dto.KafkaMovieDTO;


import java.io.IOException;
import java.util.List;

public interface MovieService {
    List<Movie> getMovies(String filter);
    List<MovieDTO> getMovies();
    Movie movieDetails(Long movieId);
    void updateMovieList(KafkaMovieDTO movie) throws IOException;
}
