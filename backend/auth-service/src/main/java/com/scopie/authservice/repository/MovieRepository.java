package com.scopie.authservice.repository;

import com.scopie.authservice.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query(value = "SELECT * FROM t_movie WHERE movie_id = ?1", nativeQuery = true)
    Movie getMovieByMovieId(long movieId);

    @Query(value = "SELECT * FROM t_movie WHERE title LIKE '%?1%' OR genre LIKE '%?1%' OR language LIKE '%?1%' ORDER BY title", nativeQuery = true)
    List<Movie> findAllWithFilter(String filter);

    @Query(value = "SELECT * FROM t_movie ORDER BY movie_id DESC LIMIT 10", nativeQuery = true)
    List<Movie> getRecentTenRecords();
}
