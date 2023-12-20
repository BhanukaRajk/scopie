package com.scopie.authservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", nullable = false)
    private long customerId;

    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;

    @Column(name = "last_name", length = 30)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "customerId", fetch = FetchType.LAZY) // USE THE ATTRIBUTE NAME (NOT THE COLUMN NAME)
    private List<Reservation> reservations;
}
