package com.scopie.authservice.repository;

import com.scopie.authservice.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query( value = "SELECT * FROM t_reservation WHERE customer = ?1", nativeQuery = true)
    List<Reservation> findByCustomerId(long customerId);
}
