package com.scopie.authservice.controller;

import com.scopie.authservice.dto.MyReservationDTO;
import com.scopie.authservice.dto.PaymentDTO;
import com.scopie.authservice.dto.ReservationAvailabilityDTO;
import com.scopie.authservice.dto.ReservationDTO;
import com.scopie.authservice.entity.Customer;
import com.scopie.authservice.entity.Reservation;
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
import javax.security.auth.login.CredentialException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservations")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor
@NoArgsConstructor
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private AuthService authService;

    // CREATE NEW RESERVATION FOR A CUSTOMER
    @PostMapping("/new")
    public ResponseEntity<String> addReservation(@RequestBody ReservationDTO reservationDTO) {
        try {
            reservationService.newReservation(reservationDTO);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Reservation Success!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Reservation Failed! Error:" + e);
        }
    }

    @PostMapping(value = "/availability")
    public boolean[] checkReservationAvailability(@RequestBody ReservationAvailabilityDTO reservationAvailabilityDTO) throws CannotProceedException {
        try {
            if(reservationService.checkAvailability(reservationAvailabilityDTO)) {
                return reservationService.getAvailability(reservationAvailabilityDTO);
//                return ResponseEntity.status(HttpStatus.ACCEPTED).body("Reservation Success!");
            } else {
//                return ResponseEntity.status(HttpStatus.ACCEPTED).body("Not enough seats available!");
                return new boolean[0];
            }

        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Reservation Failed! Error:" + e);
            throw new CannotProceedException("Could not get data!");
        }
    }

    // GET THE RESERVATION DATA WHEN USER REQUESTED BY SELECTING A RESERVATION
    @GetMapping("/my-reservations")
    public ResponseEntity<List<MyReservationDTO>> getMyReservations(@RequestParam String username) {
        try {
            Customer customer = authService.findByUsername(username);

            List<MyReservationDTO> myReservations = reservationService.getMyReservations(customer.getCustomerId());

            return new ResponseEntity<>(myReservations, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/cancel")
    public void cancelReservation(@RequestBody long reservationId) {
        try {
            reservationService.cancelReservation(reservationId);
        } catch (Exception e) {
            throw new NotFoundException("Requested reservation could not found!");
        }
    }

    @PostMapping("/payment")
    public ResponseEntity<String> payForReservation (@RequestBody PaymentDTO paymentDTO) {
        try {
            reservationService.doPayment(paymentDTO);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Payment successful!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not done the payment!");
        }
    }
}
