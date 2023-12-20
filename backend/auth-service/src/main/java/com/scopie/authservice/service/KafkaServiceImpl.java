package com.scopie.authservice.service;

import com.scopie.authservice.entity.Cinema;
import com.scopie.authservice.entity.MovieTime;
import com.scopie.authservice.entity.Seat;
import com.scopie.authservice.kafka.dto.KafkaCinemaDTO;
import com.scopie.authservice.kafka.dto.KafkaMovieTimeDTO;
import com.scopie.authservice.repository.*;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.stereotype.Service;

import javax.naming.CannotProceedException;
import java.util.UnknownFormatConversionException;

@Service
public class KafkaServiceImpl implements KafkaService {

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private MovieTimeRepository movieTimeRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    TimeSlotRepository timeSlotRepository;

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

    public void addNewSeat() {
        try {
            seatRepository.save(Seat.builder()
                    .build()
            );
        } catch (Exception e) {
            throw new UnknownFormatConversionException("Error while adding new seat! error code: "+ e);
        }
    }

    public void updateMovieTime(KafkaMovieTimeDTO kfkMovieTimeDTO) {
        try {
            movieTimeRepository.save(MovieTime.builder()
                    .movieTimeId(kfkMovieTimeDTO.getMovieTimeId())
                    .movieId(movieRepository.getMovieByMovieId(kfkMovieTimeDTO.getMovieId()))
                    .cinemaId(cinemaRepository.getReferenceById(kfkMovieTimeDTO.getCinemaId()))
                    .slotId(timeSlotRepository.getReferenceById(kfkMovieTimeDTO.getTimeSlotId()))
                    .seatCount(kfkMovieTimeDTO.getSeatCount())
                    .build());

            int totalSeatCount = seatRepository.findAll().size();

            for(int i = 0; i < totalSeatCount - kfkMovieTimeDTO.getSeatCount(); i++) {
                addNewSeat();
            }

        } catch (Exception e) {
            throw new ResourceNotFoundException("Unexpected error occurred!");
        }
    }


}
