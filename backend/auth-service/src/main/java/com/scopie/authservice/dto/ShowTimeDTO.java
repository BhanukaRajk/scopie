package com.scopie.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShowTimeDTO {
    private long id;
    private String name;
    private List<TimeSlotDTO> timeSlots;
}
