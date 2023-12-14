package com.scopie.authservice.dto;

import jakarta.transaction.Transactional;
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
@Transactional
public class MovieDTO {
    private long  movieId;
    private String title;
    private String banner;
    private String genre;
    private String language;
    private Time duration;
}