package com.scopie.authservice.kafka.dto;

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
public class KafkaCinemaDTO {
    private long  cinemaId;
    private String cinemaName;
    private String address;
    private String contact;
}