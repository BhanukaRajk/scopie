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
public class MovieShowsDTO {
    private long movieId;
    private String title;
    private List<ShowTimeDTO> movieShows;
}
