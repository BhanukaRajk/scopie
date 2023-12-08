package com.scopie.authservice.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KafkaReservedSeatDTO {
    private Long reservationSeatId; // UNIQUE FOR LIST
    private Long seatId; // UNIQUE FOR LIST
    private Long movieTimeId; // SAME IN LISTS
    private Long reservationId; // SAME IN LISTS
}
