package com.scopie.authservice.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KafkaReservedSeatDTO {
    private long reservationSeatId; // UNIQUE FOR LIST
    private long seatId; // UNIQUE FOR LIST
    private long movieTimeId; // SAME IN LISTS
    private long reservationId; // SAME IN LISTS
    private Date movieDate;
}
