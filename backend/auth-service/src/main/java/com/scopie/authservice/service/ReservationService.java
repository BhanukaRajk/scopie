package com.scopie.authservice.service;

import com.scopie.authservice.dto.PaymentDTO;
import com.scopie.authservice.dto.ReservationDTO;
import com.scopie.authservice.entity.Reservation;

import javax.naming.CannotProceedException;
import java.util.Optional;

public interface ReservationService {

    void newReservation(ReservationDTO reservationDTO);

    Optional<Reservation> getReservationById(long reservationId);

    void cancelReservation(long reservationId);

    void doPayment(PaymentDTO payment) throws CannotProceedException;

}
