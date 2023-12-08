package com.scopie.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Time;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {
    private long  movieId;
    private String title;
    private String banner;
    private String genre;
    private String language;
    private Time duration;
}