package com.scopie.authservice.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KafkaReservationCancelDTO {
    private Long reservationId; // SEND THE RESERVATION ID TO CANCEL THE RESERVATION FROM CINEMA SIDE
}
