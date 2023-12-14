package com.scopie.authservice.repository;

import com.scopie.authservice.entity.MovieTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieTimeRepository extends JpaRepository<MovieTime, Long> {

    @Query(value = "SELECT * FROM t_movie_timeslot_cinema WHERE movie_id = ?1 ORDER BY cinema_id", nativeQuery = true)
    List<MovieTime> findAllByMovieId(long movieId);

    @Query(value = "SELECT * FROM t_movie_timeslot_cinema WHERE cinema_id = ?1 AND movie_id = ?2 AND time_slot = ?3", nativeQuery = true)
    MovieTime findByCinemaMovieTimeslot(long cinemaId, long movieId, long timeSlotId);
}
