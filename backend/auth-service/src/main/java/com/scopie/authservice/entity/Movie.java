package com.scopie.authservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_movie")
public class Movie {

    @Id
    @Column(name = "movie_id")
    private Long movieId;

    @Column(name = "banner")
    private String banner;

    @Column(name = "duration")
    private Time duration;

    @Column(name = "genre")
    private String genre;

    @Column(name = "language")
    private String language;

    @Column(name = "title", nullable = false)
    private String title;
}
