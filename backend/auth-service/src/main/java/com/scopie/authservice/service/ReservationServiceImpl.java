package com.scopie.authservice.service;

import com.scopie.authservice.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceImpl implements ReservationService {

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
}
