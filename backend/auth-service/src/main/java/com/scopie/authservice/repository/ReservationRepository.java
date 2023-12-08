package com.scopie.authservice.repository;

import com.scopie.authservice.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Modifying
    @Query(value = "UPDATE t_reservation SET acceptance=?2 WHERE reservation_id=?1", nativeQuery = true)
    Reservation updateAcceptance(Long reservationId, boolean acceptance);
}
