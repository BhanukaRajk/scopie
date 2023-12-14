package com.scopie.authservice.dto;

import lombok.*;

import java.sql.Time;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TimeSlotDTO {
    private long slotId;
    private Time startTime;
}
