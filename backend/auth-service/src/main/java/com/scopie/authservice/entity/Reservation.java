package com.scopie.authservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "t_reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Integer reservationId;

    @Column(name = "customer", nullable = false)
    private String customer;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @Column(name = "movie_date", nullable = false)
    private Date movieDate;

    @Column(name = "acceptance", nullable = false)
    private boolean acceptance;
}