package com.scopie.authservice.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    private Integer userName;
    private Integer movieId;
    private Integer cinemaId;
    private Integer timeSlotId;
    private List<Integer> seatSelection;
    private Date movieDate;
}
