package com.scopie.authservice.repository;

import com.scopie.authservice.entity.ReservedSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ReservedSeatRepository extends JpaRepository<ReservedSeat, Long> {

    @Query(value = "SELECT COUNT(reserved_seat_id) FROM t_movietime_seat_reservation WHERE movie_time_id = ?1 AND DATE(movie_date) = DATE(?2)", nativeQuery = true)
    Integer countByMovieTimeAndDate(long movieTimeId, Date movieDate);

    @Query(value = "SELECT * FROM t_movietime_seat_reservation WHERE movie_time_id = ?1 AND DATE(movie_date) = DATE(?2)", nativeQuery = true)
    List<ReservedSeat> findByMovieTimeAndDate(long movieTimeId, Date movieDate);
}
