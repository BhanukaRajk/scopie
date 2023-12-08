package com.scopie.authservice.service;

import com.scopie.authservice.dto.ReservationDTO;
import com.scopie.authservice.entity.Reservation;

import java.util.Optional;

public interface ReservationService {
    public void reservationAcceptor(Long reservationId, boolean allowance);

    void newReservation(ReservationDTO reservationDTO);

    Optional<Reservation> getReservationById(Long reservationId);

    void cancelReservation(Long reservationId);
}
