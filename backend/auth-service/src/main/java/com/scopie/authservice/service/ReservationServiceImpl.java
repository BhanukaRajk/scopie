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
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.management.RuntimeErrorException;
import javax.naming.CannotProceedException;
import javax.naming.ServiceUnavailableException;
import java.sql.Time;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

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
    private TimeSlotRepository timeSlotRepository;

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
                    .totalPrice(priceCalculator(reservationDTO.getSeatSelection().size(), 500.00)) // DEFAULT PRICE OF THE TICKET
                    .build()
            );
            KafkaReservationDTO kfkReservation = new KafkaReservationDTO(
                    savedReservation.getReservationId(),
                    savedReservation.getDate(),
                    savedReservation.getTotalPrice()
            );
            kafkaReservationTemplate.send("New_Reservation_Topic", kfkReservation);

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
                kafkaReservedSeatTemplate.send("New_Reserved_Seat_Topic", kfkReservedSeat);
            }
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkAvailability(ReservationAvailabilityDTO reservationAvailabilityDTO) throws CannotProceedException {
        try {
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

            if (requestedSeatCount <= (movieTime.getSeatCount() - totalReservedSeatsCount)) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            throw new CannotProceedException("Could not get seat availability data!");
        }
    }

    public boolean[] getAvailability(ReservationAvailabilityDTO reservationAvailabilityDTO) {
        try {
            MovieTime movieTime = movieTimeRepository.findByCinemaMovieTimeslot(
                    reservationAvailabilityDTO.getCinemaId(),
                    reservationAvailabilityDTO.getMovieId(),
                    reservationAvailabilityDTO.getTimeSlotId()
            );

            List<ReservedSeat> reservedSeats = reservedSeatRepository.findByMovieTimeAndDate(
                    movieTime.getMovieTimeId(),
                    reservationAvailabilityDTO.getMovieDate()
            );

            int totalSeatCount = movieTime.getSeatCount();
            boolean[] seatAvailability = new boolean[totalSeatCount];
            Arrays.fill(seatAvailability, true);

            for (ReservedSeat reservedSeat : reservedSeats) {
                int seatIndex = (int) reservedSeat.getSeatId().getSeatId() - 1;
                if (seatIndex >= 0 && seatIndex < totalSeatCount) {
                    seatAvailability[seatIndex] = false;
                }
            }
            return seatAvailability;
        } catch (Exception e) {
            throw new ResourceNotFoundException("Could not get the available seat list!");
        }
    }

    public boolean acceptSeats(ReservationDTO reservationDTO) {
        try {
            List<Long> seatNumbers = reservationDTO.getSeatSelection();

            ReservationAvailabilityDTO reservationAvailabilityDTO = ReservationAvailabilityDTO.builder()
                    .movieId(reservationDTO.getMovieId())
                    .cinemaId(reservationDTO.getCinemaId())
                    .timeSlotId(reservationDTO.getTimeSlotId())
                    .seatCount(seatNumbers.size())
                    .movieDate(reservationDTO.getMovieDate())
                    .build();

            boolean[] seatAvailability = getAvailability(reservationAvailabilityDTO);

            for (Long seatNumber : seatNumbers) {
                int seatIndex = Math.toIntExact(seatNumber);
                if (!seatAvailability[seatIndex - 1]) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Could not detect currently reserved seats!");
        }
    }

    public List<MyReservationDTO> getMyReservations(long customerId, boolean upcoming) {
        try {
            List<MyReservationDTO> myReservations = new ArrayList<>();
            List<Reservation> reservations = reservationRepository.findByCustomerId(customerId);
            Date currentDate = new Date();

            // CONVERT RESERVATION ENTITIES TO MY RESERVATION DTO
            for (Reservation reservation : reservations) {
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
                        .paid(!isEmpty(reservation.getPayment()))
                        .build();

                if(myReservationDTO.getMovieDate().after(currentDate) || !upcoming) { // CHECK THE DATE IS PASSED
                    myReservations.add(myReservationDTO);
                }
            }
            return myReservations;
        } catch (Exception e) {
            throw new ResourceNotFoundException("Could not get your reservation data!");
        }
    }

    private List<Long> getSeatNumbers(List<ReservedSeat> reservedSeats) {
        return reservedSeats.stream().map(ReservedSeat::getSeatId).map(Seat::getSeatId).collect(Collectors.toList());
    }

    public Time getRequestedTime(long timeSlotId) {
        return timeSlotRepository.findTimeById(timeSlotId);
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
