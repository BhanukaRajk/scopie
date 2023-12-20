package com.scopie.authservice.service;

import com.scopie.authservice.dto.MovieDTO;
import com.scopie.authservice.dto.MovieShowsDTO;
import com.scopie.authservice.entity.Movie;
import com.scopie.authservice.kafka.dto.KafkaMovieDTO;


import java.io.IOException;
import java.util.List;

public interface MovieService {
    List<MovieDTO> getMovies(String filter);
    List<MovieDTO> getMovies(boolean recents);
    MovieShowsDTO movieDetails(long movieId);
    void updateMovieList(KafkaMovieDTO movie) throws IOException;
}
