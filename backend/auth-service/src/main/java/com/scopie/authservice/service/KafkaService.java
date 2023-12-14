package com.scopie.authservice.service;

import com.scopie.authservice.kafka.dto.KafkaCinemaDTO;

import javax.naming.CannotProceedException;

public interface KafkaService {

    void updateCinema(KafkaCinemaDTO kfkCinemaDTO) throws CannotProceedException;

    void addNewSeat(long seatId);
}
