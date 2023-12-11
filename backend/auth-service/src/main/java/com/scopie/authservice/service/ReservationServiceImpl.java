package com.scopie.authservice.service;

import com.scopie.authservice.dto.PaymentDTO;
import com.scopie.authservice.dto.ReservationDTO;
import com.scopie.authservice.entity.Payment;
import com.scopie.authservice.entity.Reservation;
import com.scopie.authservice.repository.PaymentRepository;
import com.scopie.authservice.repository.ReservationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.naming.CannotProceedException;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    ModelMapper modelMapper = new ModelMapper();
    
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    // RESERVATION PRICE CALCULATOR
    public Double priceCalculator(Integer seatCount, Double seatType) {
        return seatCount*seatType;
    }


    // ACCEPT USING CHANGE THE COLUMN VALUE OF ACCEPTANCE
    public void reservationAcceptor(Long reservationId, boolean allowance) {
        reservationRepository.updateAcceptance(reservationId, allowance);
    }

    // CREATE NEW RESERVATION IN DATABASE AS WELL AS CINEMA SIDE
    public void newReservation(ReservationDTO reservationDTO) {
        Double total = priceCalculator(100, 2000.00); // TODO: CHANGE THESE VALUES WITH CUSTOMER REQUESTS
        reservationRepository.save(modelMapper.map(reservationDTO, Reservation.class));
        // TODO: SEND THE DATA TO CINEMA SIDE VIA KAFKA
        kafkaTemplate.send("NewReservations", reservationDTO.toString());
    }

    public Optional<Reservation> getReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId);
    }

    // CANCEL THE RESERVATION FROM DATABASE AS WELL AS CINEMA SIDE
    public void cancelReservation(Long reservationId) {
        reservationRepository.deleteById(reservationId);
        // TODO: PASS THE CANCELLING RESERVATION ID TO THE CINEMA SIDE
        kafkaTemplate.send("CancelReservation", reservationId.toString());
    }

    // COMPLETE THE PAYMENT
   public void doPayment(PaymentDTO payment) throws CannotProceedException {
       try {
           paymentRepository.save(Payment.builder()
                   .reservation(reservationRepository.getReferenceById(payment.getReservationId()))
                   .build()
           ); // TODO: CHECK THIS WORKING OR NOT?
       } catch (Exception e) {
           throw new CannotProceedException("Could not update the database!");
       }
   }

}
