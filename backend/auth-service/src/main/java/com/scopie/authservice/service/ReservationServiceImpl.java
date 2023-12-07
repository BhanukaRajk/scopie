package com.scopie.authservice.service;

import com.scopie.authservice.dto.ReservationDTO;
import com.scopie.authservice.entity.Reservation;
import com.scopie.authservice.repository.ReservationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    ModelMapper modelMapper = new ModelMapper();
    
    @Autowired
    private ReservationRepository reservationRepository;

    // RESERVATION PRICE CALCULATOR
    public Double priceCalculator(Integer seatCount, Double seatType) {
        return seatCount*seatType;
    }

    public void reservationRequest() {
        // SEND REQUEST USING KAFKA
    }

    // ACCEPT USING CHANGE THE COLUMN VALUE OF ACCEPTANCE
    public void reservationAcceptor(Integer reservationId, boolean allowance) {
        reservationRepository.updateAcceptance(reservationId, allowance);
    }

    public void newReservation(ReservationDTO reservationDTO) {
        Double total = priceCalculator(100, 2000.00); // TODO: CHANGE THESE VALUES WITH CUSTOMER REQUESTS
        // TODO: SEND THE DATA TO CINEMA SIDE VIA KAFKA
        reservationRepository.save(modelMapper.map(reservationDTO, Reservation.class));
    }

    public Optional<Reservation> getReservationById(Integer reservationId) {
        return reservationRepository.findById(reservationId);
    }

    public void cancelReservation(Integer reservationId) {
        reservationRepository.deleteById(reservationId);
    }

}
