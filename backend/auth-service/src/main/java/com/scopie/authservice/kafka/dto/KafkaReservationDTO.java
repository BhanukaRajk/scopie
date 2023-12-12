package com.scopie.authservice.kafka.dto;

import lombok.*;
import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KafkaReservationDTO {
    private long reservationId;
    private Date date;
    private Double totalPrice;
}
