package com.scopie.authservice.controller;

import com.scopie.authservice.component.JwtGenerator;
import com.scopie.authservice.dto.MyReservationDTO;
import com.scopie.authservice.dto.PaymentDTO;
import com.scopie.authservice.dto.ReservationAvailabilityDTO;
import com.scopie.authservice.dto.ReservationDTO;
import com.scopie.authservice.entity.Customer;
import com.scopie.authservice.service.AuthService;
import com.scopie.authservice.service.ReservationService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.CannotProceedException;
import java.sql.Time;
import java.util.*;

@RestController
@RequestMapping("/api/reservations")
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*")
@AllArgsConstructor
@NoArgsConstructor
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtGenerator jwtGenerator;


    // CREATE NEW RESERVATION FOR A CUSTOMER
    @PostMapping("/new")
    public ResponseEntity<String> addReservation(@RequestBody ReservationDTO reservationDTO, @RequestHeader(name = "Authorization", required = true) String authorizationHeader) {
        if((jwtGenerator.validateToken(authorizationHeader.split(" ")[1]))) {
            Date currentDate = new Date();
            Date requestedDate = reservationDTO.getMovieDate();


            System.out.println("Current date: " + currentDate.getTime());
            System.out.println("Requested date: " + requestedDate);

            Time requestedTime = reservationService.getRequestedTime(reservationDTO.getTimeSlotId());

            if (!requestedDate.after(currentDate) || (requestedDate.equals(currentDate) && requestedTime.before(currentDate))) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Please enter valid date!");
            } else if (!reservationService.acceptSeats(reservationDTO)) { // CHECK THE USER TRIES TO GET CURRENTLY RESERVED SEATS
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Sorry! Some of requested seats are currently reserved!");
            } else {
                try {
                    reservationService.newReservation(reservationDTO);
                    return ResponseEntity.status(HttpStatus.ACCEPTED).body("Reservation Success!");
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Reservation Failed! Error:" + e);
                }
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized user!");
        }
    }

    @PostMapping(value = "/availability")
    public boolean[] checkReservationAvailability(@RequestBody ReservationAvailabilityDTO
                                                          reservationAvailabilityDTO, @RequestHeader(name = "Authorization", required = true) String authorizationHeader) throws CannotProceedException {
        if((jwtGenerator.validateToken(authorizationHeader.split(" ")[1]))) {
            try {
                if (reservationService.checkAvailability(reservationAvailabilityDTO)) {
                    return reservationService.getAvailability(reservationAvailabilityDTO);
                } else {
                    return new boolean[0];
                }
            } catch (Exception e) {
                throw new CannotProceedException("Could not get data!");
            }
        } else {
            throw new RuntimeException("Unauthorized user!");
        }
    }

    // GET THE RESERVATION DATA OF GIVEN USER
    @GetMapping("/my-reservations")
    public ResponseEntity<List<MyReservationDTO>> getMyReservations(@RequestParam String username, @RequestHeader(name = "Authorization", required = true) String authorizationHeader) {
        if((jwtGenerator.validateToken(authorizationHeader.split(" ")[1]))) {
            try {
                Customer customer = authService.findByUsername(username);
                List<MyReservationDTO> myReservations = reservationService.getMyReservations(customer.getCustomerId(), false);

                return new ResponseEntity<>(myReservations, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    // GET THE RESERVATION DATA WHEN USER REQUESTED BY SELECTING A RESERVATION
    @GetMapping("/upcoming-reservations")
    public ResponseEntity<List<MyReservationDTO>> getUpcomingReservations(@RequestParam String username, @RequestHeader(name = "Authorization", required = true) String authorizationHeader) {
        if((jwtGenerator.validateToken(authorizationHeader.split(" ")[1]))) {
            try {
                Customer customer = authService.findByUsername(username);
                List<MyReservationDTO> myReservations = reservationService.getMyReservations(customer.getCustomerId(), true);
                return new ResponseEntity<>(myReservations, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

    }


    // ADDITIONAL COMPONENTS
    @DeleteMapping("/cancel")
    public void cancelReservation(@RequestBody long reservationId, @RequestHeader(name = "Authorization", required = true) String authorizationHeader) {
        if((jwtGenerator.validateToken(authorizationHeader.split(" ")[1]))) {
            try {
                reservationService.cancelReservation(reservationId);
            } catch (Exception e) {
                throw new NotFoundException("Requested reservation could not found!");
            }
        } else {
            throw new RuntimeException("Unauthorized user!");
        }
    }

    @PostMapping("/payment")
    public ResponseEntity<String> payForReservation(@RequestBody PaymentDTO paymentDTO, @RequestHeader(name = "Authorization", required = true) String authorizationHeader) {
        if((jwtGenerator.validateToken(authorizationHeader.split(" ")[1]))) {
            try {
                reservationService.doPayment(paymentDTO);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("Payment successful!");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not done the payment!");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized user!");
        }
    }
}
