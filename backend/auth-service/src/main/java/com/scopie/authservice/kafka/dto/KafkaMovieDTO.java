package com.scopie.authservice.kafka.dto;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Time;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KafkaMovieDTO {
    private long  movieId;
    private String title;
    private MultipartFile banner;
    private String genre;
    private String language;
    private Time duration;
}