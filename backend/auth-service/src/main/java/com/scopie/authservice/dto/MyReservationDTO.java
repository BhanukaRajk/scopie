package com.scopie.authservice.dto;

import lombok.*;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyReservationDTO {
    private Long customerId;
    private String movieName;
    private Long reservationId;
    private Double totalPrice;
    private Date bookedOn; // DATE WHEN CREATED
    private List<Long> seatNumbers;
    private Time movieTime;
    private Date movieDate; // DATE WHEN MOVIE SHOWS

}