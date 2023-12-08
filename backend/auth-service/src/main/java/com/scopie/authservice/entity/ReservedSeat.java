package com.scopie.authservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Long reservedSeatId;

    @Column(name = "movie_time_id", nullable = false)
    private Integer movieTimeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "reservation_id", name = "reservation_id", nullable = false)
    private Reservation reservationId;

    @Column(name = "seat_id", nullable = false)
    private Integer seatId;

//    @Column(name = "booking_status", nullable = false) // BOOKED OR NOT
//    private boolean bookingStatus;

}
