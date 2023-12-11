package com.scopie.authservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "reservation_id", name = "reservation", nullable = false)
    private Reservation reservation;

    @Column(name= "payment_date", nullable = false)
    @CreationTimestamp
    private Timestamp paymentDate;

}
