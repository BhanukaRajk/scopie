package com.scopie.authservice.controller;

import com.scopie.authservice.service.ReservationService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.CannotProceedException;

@RestController
@RequestMapping("/api/reservation")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor
@NoArgsConstructor
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PatchMapping("/confirmation")
    public void reservationStatusHandler(Integer reservationId, boolean confirmation) throws CannotProceedException {
        try {
            reservationService.reservationAcceptor(reservationId, confirmation);
        } catch(Exception e) {
            throw new CannotProceedException();
        }
    }
}
