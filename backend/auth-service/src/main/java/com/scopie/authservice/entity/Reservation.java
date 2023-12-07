package com.scopie.authservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "customer_id", name = "customer", nullable = false)
    private Customer customer;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @Column(name = "movie_date", nullable = false)
    private Date movieDate;

    @Column(name = "acceptance", nullable = false)
    private boolean acceptance;

    @OneToMany(mappedBy = "reservationId", fetch = FetchType.LAZY)
    private List<ReservedSeat> seats;
}