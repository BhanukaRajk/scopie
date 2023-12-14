package com.scopie.authservice.dto;

import lombok.*;

import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationAvailabilityDTO {
    private long movieId;
    private long cinemaId;
    private long timeSlotId;
    private int seatCount;
    private Date movieDate;
}
