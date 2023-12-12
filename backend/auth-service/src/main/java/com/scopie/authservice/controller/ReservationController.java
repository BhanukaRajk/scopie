package com.scopie.authservice.controller;

import com.scopie.authservice.dto.PaymentDTO;
import com.scopie.authservice.dto.ReservationDTO;
import com.scopie.authservice.entity.Reservation;
import com.scopie.authservice.service.ReservationService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.CannotProceedException;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservation")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor
@NoArgsConstructor
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

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

    // GET THE RESERVATION DATA WHEN USER REQUESTED BY SELECTING A RESERVATION
    @GetMapping("/:id")
    public Optional<Reservation> viewSpecificReservation(@RequestParam long reservationId) throws NotFoundException {
        try {
            return reservationService.getReservationById(reservationId);
        } catch (Exception e) {
            throw new NotFoundException("Reservation not found!");
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
