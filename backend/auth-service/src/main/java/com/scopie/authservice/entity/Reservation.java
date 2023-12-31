package com.scopie.authservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private long reservationId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "customer_id", name = "customer", nullable = false)
    private Customer customerId;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @Column(name = "created_date", nullable = false)
    @CreationTimestamp
    private Date date;


    @OneToMany(mappedBy = "reservationId", fetch = FetchType.EAGER)
    private List<ReservedSeat> reservedSeats;

    @OneToOne(mappedBy = "reservation", fetch = FetchType.LAZY)
    private Payment payment;
}