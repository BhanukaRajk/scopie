package com.scopie.authservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "t_movie_timeslot_cinema")
public class MovieTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_time_id", nullable = false)
    private Integer movieTimeId;

    @Column(name = "movie_id", nullable = false)
    private Integer movieId;

    @Column(name = "cinema_id")
    private Integer cinemaId;

    @Column(name = "time_slot")
    private Integer timeSlot;

    @Column(name = "seat_count")
    private String seatCount;

}
