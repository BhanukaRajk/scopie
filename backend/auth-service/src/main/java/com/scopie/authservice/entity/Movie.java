package com.scopie.authservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Duration;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "t_movie")
public class Movie {

    @Id
    @Column(name = "movie_id")
    private Integer movieId;

    @Column(name = "banner")
    private String banner;

    @Column(name = "duration")
    private Time duration;

    @Column(name = "genre")
    private String genre;

    @Column(name = "language")
    private String language;

    @Column(name = "title")
    private String title;
}