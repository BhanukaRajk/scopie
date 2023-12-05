package com.scopie.authservice.repository;

import com.scopie.authservice.entity.Movie;
import com.scopie.authservice.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    @Query(value = "SELECT * FROM t_movies WHERE movie_id = ?1", nativeQuery = true)
    public Reservation getMovieByMovieId(Integer movieId);
}
