package com.scopie.authservice.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KafkaMovieTimeDTO {
    private long  movieTimeId;
    private long  timeSlotId;
    private long cinemaId;
    private long  movieId;
    private int seatCount;
}