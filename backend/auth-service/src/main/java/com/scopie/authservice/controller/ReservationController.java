package com.scopie.authservice.controller;

import com.scopie.authservice.dto.ReservationDTO;
import com.scopie.authservice.entity.Reservation;
import com.scopie.authservice.service.ReservationService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.naming.CannotProceedException;
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
    public void addReservation(@RequestBody ReservationDTO reservationDTO) throws NoSuchMethodException {
        try {
            reservationService.newReservation(reservationDTO);
        } catch (Exception e) {
            throw new NoSuchMethodException();
        }
    }

    // GET THE RESERVATION DATA WHEN USER REQUESTED BY SELECTING A RESERVATION
    @GetMapping("/:id")
    public Optional<Reservation> viewSpecificReservation(@RequestParam Integer reservationId) throws NotFoundException {
        try {
            return reservationService.getReservationById(reservationId);
        } catch (Exception e) {
            throw new NotFoundException("Reservation not found!");
        }
    }

    @PatchMapping("/confirmation")
    public void reservationStatusHandler(Integer reservationId, boolean confirmation) throws CannotProceedException {
        try {
            reservationService.reservationAcceptor(reservationId, confirmation);
        } catch(Exception e) {
            throw new CannotProceedException();
        }
    }

    @DeleteMapping("/:id")
    public void cancelReservation(@RequestBody Integer reservationId) {
        try {
            reservationService.cancelReservation(reservationId);
        } catch (Exception e) {
            throw new NotFoundException("Requested reservation could not found!");
        }
    }
}
