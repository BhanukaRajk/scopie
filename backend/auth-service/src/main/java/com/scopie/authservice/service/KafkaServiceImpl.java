package com.scopie.authservice.service;

import com.scopie.authservice.entity.Cinema;
import com.scopie.authservice.entity.Seat;
import com.scopie.authservice.kafka.dto.KafkaCinemaDTO;
import com.scopie.authservice.kafka.dto.KafkaMovieTimeDTO;
import com.scopie.authservice.repository.CinemaRepository;
import com.scopie.authservice.repository.MovieTimeRepository;
import com.scopie.authservice.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.CannotProceedException;
import java.util.UnknownFormatConversionException;

@Service
public class KafkaServiceImpl implements KafkaService {

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private SeatRepository seatRepository;

    public void updateCinema(KafkaCinemaDTO kfkCinemaDTO) throws CannotProceedException {
        try {
            cinemaRepository.save(Cinema.builder()
                    .id(kfkCinemaDTO.getCinemaId())
                    .name(kfkCinemaDTO.getCinemaName())
                    .address(kfkCinemaDTO.getAddress())
                    .contact(kfkCinemaDTO.getContact())
                    .build()
            );
        } catch (Exception e) {
            throw new CannotProceedException("Could not update the database!");
        }
    }

    public void addNewSeat(long seatId) {
        try {
            seatRepository.save(Seat.builder()
                    .seatId(seatId)
                    .build()
            );
        } catch (Exception e) {
            throw new UnknownFormatConversionException("Error while adding new seat! error code: "+ e);
        }
    }

}
