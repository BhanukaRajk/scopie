package com.scopie.authservice.service;

import com.scopie.authservice.dto.PaymentDTO;
import com.scopie.authservice.dto.ReservationDTO;
import com.scopie.authservice.entity.Customer;
import com.scopie.authservice.entity.Payment;
import com.scopie.authservice.entity.Reservation;
import com.scopie.authservice.entity.ReservedSeat;
import com.scopie.authservice.kafka.dto.KafkaReservationDTO;
import com.scopie.authservice.kafka.dto.KafkaReservedSeatDTO;
import com.scopie.authservice.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.naming.CannotProceedException;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    ModelMapper modelMapper = new ModelMapper();
    
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservedSeatRepository reservedSeatRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MovieTimeRepository movieTimeRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    KafkaTemplate<String, Integer> kafkaCancellationTemplate;

    @Autowired
    KafkaTemplate<String, KafkaReservationDTO> kafkaReservationTemplate;

    @Autowired
    KafkaTemplate<String, KafkaReservedSeatDTO> kafkaReservedSeatTemplate;


    // RESERVATION PRICE CALCULATOR
    public Double priceCalculator(Integer seatCount, Double seatType) {
        return seatCount*seatType;
    }



    // CREATE NEW RESERVATION IN DATABASE AS WELL AS CINEMA SIDE
    public void newReservation(ReservationDTO reservationDTO) {

        Reservation savedReservation = reservationRepository.save(Reservation.builder()
                        .customerId(customerRepository.findByEmail(reservationDTO.getUserName()))
                        .date(reservationDTO.getMovieDate())
                        .totalPrice(reservationDTO.getSeatSelection().size()*500.00)
                    .build()
        );
        KafkaReservationDTO kfkReservation = new KafkaReservationDTO(
                savedReservation.getReservationId(),
                savedReservation.getDate(),
                savedReservation.getTotalPrice()
        );
        // TODO: SEND THE DATA TO CINEMA SIDE VIA KAFKA
        kafkaReservationTemplate.send("NewReservations", kfkReservation);


        for(Long seat : reservationDTO.getSeatSelection()) {
            ReservedSeat reservedSeat = reservedSeatRepository.save(ReservedSeat.builder()
                    .movieTimeId(movieTimeRepository.findById(reservationDTO.getTimeSlotId()).orElseThrow())
                    .reservationId(savedReservation)
                    .seatId(seatRepository.findById(seat).orElseThrow())
                    .build()
            );

            KafkaReservedSeatDTO kfkReservedSeat = new KafkaReservedSeatDTO(
                    reservedSeat.getReservedSeatId(),
                    reservedSeat.getSeatId().getSeatId(),
                    reservedSeat.getMovieTimeId().getMovieTimeId(),
                    reservedSeat.getReservationId().getReservationId()
            );
            kafkaReservedSeatTemplate.send("NewSeatReserve", kfkReservedSeat);
        }
    }

    public Optional<Reservation> getReservationById(long reservationId) {
        return reservationRepository.findById(reservationId);
    }

    // CANCEL THE RESERVATION FROM DATABASE AS WELL AS CINEMA SIDE
    public void cancelReservation(long reservationId) {
        reservationRepository.deleteById(reservationId);
        // TODO: PASS THE CANCELLING RESERVATION ID TO THE CINEMA SIDE
//        kafkaTemplate.send("CancelReservation", reservationId.toString());
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
