package com.scopie.authservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
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
    private Long reservationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "customer_id", name = "customer", nullable = false)
    private Customer customerId;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @Column(name = "created_date", nullable = false)
    @CreationTimestamp
    private Date date;

//    @Column(name = "acceptance", nullable = false)
//    private boolean acceptance;

    @OneToMany(mappedBy = "reservationId", fetch = FetchType.LAZY)
    private List<ReservedSeat> seats;
}