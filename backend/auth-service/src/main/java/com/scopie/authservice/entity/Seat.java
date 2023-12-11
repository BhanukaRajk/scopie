package com.scopie.authservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_seat")
public class Seat {

    @Id
    @Column(name = "seat_id")
    private Long seatId;

    @OneToMany(mappedBy = "seatId", fetch = FetchType.LAZY)
    private List<ReservedSeat> reservedSeats;
}
