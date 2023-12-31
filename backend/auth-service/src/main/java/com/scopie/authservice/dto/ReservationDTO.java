package com.scopie.authservice.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    private String userName;
    private long movieId;
    private long cinemaId;
    private long timeSlotId;
    private List<Long> seatSelection;
    private Date movieDate;
}
