package com.scopie.authservice.dto;

import lombok.*;
import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeatRequestDTO {
    private Integer movieId;
    private Integer cinemaId;
    private Integer timeSlotId;
    private Date date;
}
