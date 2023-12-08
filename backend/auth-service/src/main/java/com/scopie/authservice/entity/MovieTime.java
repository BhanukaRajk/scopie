package com.scopie.authservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_movie_timeslot_cinema")
public class MovieTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_time_id", nullable = false)
    private Long movieTimeId;

    @Column(name = "movie_id", nullable = false)
    private Integer movieId;

    @Column(name = "cinema_id")
    private Integer id;

    @Column(name = "time_slot")
    private Integer slotId;

    @Column(name = "seat_count", nullable = false)
    private String seatCount;

}
