package com.scopie.authservice.kafka.dto;

import lombok.*;
import java.sql.Date;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KafkaReservationDTO {
    private Long reservationId;
    private Date date;
    private Double totalPrice;
}
