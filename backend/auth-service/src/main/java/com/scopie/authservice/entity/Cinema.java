package com.scopie.authservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_cinema")
public class Cinema {

    @Id
    @Column(name = "cinema_id")
    private Long id;

    @Column(name = "cinema_name", nullable = false)
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "contact", length = 10)
    private String contact;


    @OneToMany(mappedBy = "cinemaId", fetch = FetchType.LAZY) // USE THE ATTRIBUTE NAME (NOT THE COLUMN NAME)
    private List<MovieTime> movieTimes;
}
