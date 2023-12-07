package com.scopie.authservice.service;

import com.scopie.authservice.entity.Movie;

import java.util.List;

public interface MovieService {
    List<Movie> getMovies(String filter);
    List<Movie> getMovies();
    Movie movieDetails(Integer movieId);
    Movie updateMovieList(Movie movie);
}
