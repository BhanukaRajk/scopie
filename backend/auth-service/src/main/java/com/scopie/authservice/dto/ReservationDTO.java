package com.scopie.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
public class ReservationDTO {
    private String userName;
    private Long movieId;
    private Long cinemaId;
    private List<Long> seatSelection;
    private Date movieDate;
}
