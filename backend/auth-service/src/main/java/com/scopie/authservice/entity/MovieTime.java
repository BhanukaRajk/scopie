package com.scopie.authservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_movie_timeslot_cinema")
public class MovieTime {

    @Id
    @Column(name = "movie_time_id", nullable = false)
    private long movieTimeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "movie_id", name = "movie_id", nullable = false)
    private Movie movieId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "cinema_id", name = "cinema_id", nullable = false)
    private Cinema cinemaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "slot_id", name = "time_slot", nullable = false)
    private TimeSlot slotId;

    @Column(name = "seat_count", nullable = false)
    private String seatCount;


    @OneToMany(mappedBy = "movieTimeId", fetch = FetchType.LAZY)
    private List<ReservedSeat> reservedSeats;

}
