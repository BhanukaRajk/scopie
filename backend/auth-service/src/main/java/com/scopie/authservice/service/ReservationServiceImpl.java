package com.scopie.authservice.service;

import com.scopie.authservice.dto.MyReservationDTO;
import com.scopie.authservice.dto.PaymentDTO;
import com.scopie.authservice.dto.ReservationAvailabilityDTO;
import com.scopie.authservice.dto.ReservationDTO;
import com.scopie.authservice.entity.*;
import com.scopie.authservice.kafka.dto.KafkaReservationDTO;
import com.scopie.authservice.kafka.dto.KafkaReservedSeatDTO;
import com.scopie.authservice.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.naming.CannotProceedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

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
    KafkaTemplate<String, Long> kafkaCancellationTemplate;

    @Autowired
    KafkaTemplate<String, KafkaReservationDTO> kafkaReservationTemplate;

    @Autowired
    KafkaTemplate<String, KafkaReservedSeatDTO> kafkaReservedSeatTemplate;


    // RESERVATION PRICE CALCULATOR
    public Double priceCalculator(Integer seatCount, Double seatType) {
        return seatCount * seatType;
    }


    // CREATE NEW RESERVATION IN DATABASE AS WELL AS CINEMA SIDE
    @Transactional
    public boolean newReservation(ReservationDTO reservationDTO) {

        try {
            // SET RESERVATION
            Reservation savedReservation = reservationRepository.save(Reservation.builder()
                    .customerId(customerRepository.findByEmail(reservationDTO.getUserName()))
                    .date(reservationDTO.getMovieDate())
                    .totalPrice(reservationDTO.getSeatSelection().size() * 500.00)
                    .build()
            );
            KafkaReservationDTO kfkReservation = new KafkaReservationDTO(
                    savedReservation.getReservationId(),
                    savedReservation.getDate(),
                    savedReservation.getTotalPrice()
            );
            kafkaReservationTemplate.send("NewReservations", kfkReservation);

            // SET SEATS
            for (Long seat : reservationDTO.getSeatSelection()) {
                ReservedSeat reservedSeat = reservedSeatRepository.save(ReservedSeat.builder()
                        .movieTimeId(movieTimeRepository.findByCinemaMovieTimeslot(
                                reservationDTO.getCinemaId(),
                                reservationDTO.getMovieId(),
                                reservationDTO.getTimeSlotId()
                        ))
                        .reservationId(savedReservation)
                        .seatId(seatRepository.findById(seat).orElseThrow())
                        .movieDate(reservationDTO.getMovieDate())
                        .build()
                );

                KafkaReservedSeatDTO kfkReservedSeat = new KafkaReservedSeatDTO(
                        reservedSeat.getReservedSeatId(),
                        reservedSeat.getSeatId().getSeatId(),
                        reservedSeat.getMovieTimeId().getMovieTimeId(),
                        reservedSeat.getReservationId().getReservationId(),
                        reservedSeat.getMovieDate()
                );
                kafkaReservedSeatTemplate.send("NewSeatReserve", kfkReservedSeat);
            }

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkAvailability(ReservationAvailabilityDTO reservationAvailabilityDTO) {
        MovieTime movieTime = movieTimeRepository.findByCinemaMovieTimeslot(
                reservationAvailabilityDTO.getCinemaId(),
                reservationAvailabilityDTO.getMovieId(),
                reservationAvailabilityDTO.getTimeSlotId()
        );

        List<ReservedSeat> reservedSeats = reservedSeatRepository.findByMovieTimeAndDate(
                movieTime.getMovieTimeId(),
                reservationAvailabilityDTO.getMovieDate()
        );

        int totalReservedSeatsCount = reservedSeats.size();
        int requestedSeatCount = reservationAvailabilityDTO.getSeatCount();

        System.out.println(totalReservedSeatsCount);

        if (requestedSeatCount <= (Integer.parseInt(movieTime.getSeatCount()) - totalReservedSeatsCount)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean[] getAvailability(ReservationAvailabilityDTO reservationAvailabilityDTO) {
        MovieTime movieTime = movieTimeRepository.findByCinemaMovieTimeslot(
                reservationAvailabilityDTO.getCinemaId(),
                reservationAvailabilityDTO.getMovieId(),
                reservationAvailabilityDTO.getTimeSlotId()
        );

        List<ReservedSeat> reservedSeats = reservedSeatRepository.findByMovieTimeAndDate(
                movieTime.getMovieTimeId(),
                reservationAvailabilityDTO.getMovieDate()
        );

        int totalSeatCount = Integer.parseInt(movieTime.getSeatCount());

        boolean[] seatAvailability = new boolean[totalSeatCount];
        Arrays.fill(seatAvailability, true);

        for (ReservedSeat reservedSeat : reservedSeats) {
            int seatIndex = (int) reservedSeat.getSeatId().getSeatId() - 1;
            if (seatIndex >= 0 && seatIndex < totalSeatCount) {
                seatAvailability[seatIndex] = false;
            }
        }

        return seatAvailability;
    }

    public List<MyReservationDTO> getMyReservations(long customerId) {

        List<MyReservationDTO> myReservations = new ArrayList<>();
        List<Reservation> reservations = reservationRepository.findByCustomerId(customerId);
        System.out.println(reservations.size());

        // CONVERT RESERVATION ENTITIES TO MY RESERVATION DTO
        for (Reservation reservation : reservations) {
            System.out.println("HI line 170");
            MyReservationDTO myReservationDTO = MyReservationDTO.builder()
                    .customerId(reservation.getCustomerId().getCustomerId())
                    .title(reservation.getReservedSeats().get(0).getMovieTimeId().getMovieId().getTitle())
                    .name(reservation.getReservedSeats().get(0).getMovieTimeId().getCinemaId().getName())
                    .reservationId(reservation.getReservationId())
                    .totalPrice(reservation.getTotalPrice())
                    .date(reservation.getDate())
                    .seatNumbers(getSeatNumbers(reservation.getReservedSeats()))
                    .movieTime(reservation.getReservedSeats().get(0).getMovieTimeId().getSlotId().getStartTime())
                    .movieDate(reservation.getReservedSeats().get(0).getMovieDate())
                    .build();
            myReservations.add(myReservationDTO);
        }

        return myReservations;
    }

    private List<Long> getSeatNumbers(List<ReservedSeat> reservedSeats) {
        System.out.println("HI IM HERE");
        return reservedSeats.stream().map(ReservedSeat::getSeatId).map(Seat::getSeatId).collect(Collectors.toList());
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
