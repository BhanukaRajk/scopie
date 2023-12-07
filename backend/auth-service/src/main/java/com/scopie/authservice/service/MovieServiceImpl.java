package com.scopie.authservice.service;

import com.scopie.authservice.entity.Movie;
import com.scopie.authservice.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService{

    @Autowired
    private MovieRepository movieRepository;

    // GET MOVIES WHEN FILTER KEY IS AVAILABLE
    public List<Movie> getMovies(String filter) {
        return movieRepository.findAllWithFilter(filter);
    }

    // GET MOVIES WHEN FILTER KEY IS NOT AVAILABLE
    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }

    // GET THE MOVIE DETAILS WHEN USER CLICK ON SOME MOVIE
    public Movie movieDetails(Integer movieId) {
        return movieRepository.getMovieByMovieId(movieId);
    }

    // ADD OR UPDATE MOVIES
    public Movie updateMovieList(Movie movie) {
//        Optional<Movie> newMovie = movieRepository.findById(movie.getMovieId());
        return movieRepository.save(movie);
    }
}
