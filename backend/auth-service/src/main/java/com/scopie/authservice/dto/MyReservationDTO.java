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
    private long customerId;
    private String title; // MOVIE NAME
    private String name;// CINEMA NAME
    private long reservationId;
    private Double totalPrice;
    private Date date; // DATE WHEN CREATED
    private List<Long> seatNumbers; // SEAT IDS IN SEAT RESERVATION TABLE
    private Time movieTime;
    private Date movieDate; // DATE WHEN MOVIE SHOWS

}
