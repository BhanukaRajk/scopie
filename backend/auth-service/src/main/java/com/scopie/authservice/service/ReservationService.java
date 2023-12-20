package com.scopie.authservice.service;

import com.scopie.authservice.dto.MyReservationDTO;
import com.scopie.authservice.dto.PaymentDTO;
import com.scopie.authservice.dto.ReservationAvailabilityDTO;
import com.scopie.authservice.dto.ReservationDTO;
import com.scopie.authservice.entity.Reservation;

import javax.naming.CannotProceedException;
import javax.naming.ServiceUnavailableException;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

public interface ReservationService {

    boolean newReservation(ReservationDTO reservationDTO) throws ServiceUnavailableException;

    void cancelReservation(long reservationId);

    void doPayment(PaymentDTO payment) throws CannotProceedException;

    boolean checkAvailability(ReservationAvailabilityDTO reservationAvailabilityDTO) throws CannotProceedException;

    boolean[] getAvailability(ReservationAvailabilityDTO reservationAvailabilityDTO);

    List<MyReservationDTO> getMyReservations(long customerId, boolean upcoming);

    boolean acceptSeats(ReservationDTO reservationDTO);

    Time getRequestedTime(long timeSlotId);
}
