package com.scopie.authservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_movietime_seat_reservation")
public class ReservedSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserved_seat_id")
    private long reservedSeatId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "movie_time_id", name = "movie_time_id", nullable = false)
    private MovieTime movieTimeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "reservation_id", name = "reservation_id", nullable = false)
    private Reservation reservationId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "seat_id", name = "seat_id", nullable = false)
    private Seat seatId;

    @Column(name = "movie_date", nullable = false)
    private Date movieDate;

}
