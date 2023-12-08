package com.scopie.authservice.repository;

import com.scopie.authservice.entity.Movie;
import com.scopie.authservice.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query(value = "SELECT * FROM t_movie WHERE movie_id = ?1", nativeQuery = true)
    Movie getMovieByMovieId(Long movieId);

    @Query(value = "SELECT * FROM t_movie WHERE title LIKE '%?1%' OR genre LIKE '%?1%' OR language LIKE '%?1%'ORDER BY title", nativeQuery = true)
    List<Movie> findAllWithFilter(String filter);
}
