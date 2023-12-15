package com.scopie.authservice.service;

import com.scopie.authservice.dto.MovieDTO;
import com.scopie.authservice.dto.MovieShowsDTO;
import com.scopie.authservice.dto.ShowTimeDTO;
import com.scopie.authservice.dto.TimeSlotDTO;
import com.scopie.authservice.entity.Cinema;
import com.scopie.authservice.entity.MovieTime;
import com.scopie.authservice.entity.TimeSlot;
import com.scopie.authservice.kafka.dto.KafkaMovieDTO;
import com.scopie.authservice.entity.Movie;
import com.scopie.authservice.repository.MovieRepository;
import com.scopie.authservice.repository.MovieTimeRepository;
import com.scopie.authservice.repository.TimeSlotRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieTimeRepository movieTimeRepository;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${upload.path}")
    private String FILE_PATH;


    // GET MOVIES WHEN FILTER KEY IS AVAILABLE
    public List<MovieDTO> getMovies(String filter) {
        List<Movie> movies = movieRepository.findAllWithFilter(filter);
        return movies.stream()
                .map(movie -> new MovieDTO(
                        movie.getMovieId(),
                        movie.getTitle(),
                        movie.getBanner(),
                        movie.getGenre(),
                        movie.getLanguage(),
                        movie.getDuration()
                )).toList();
    }

    // GET MOVIES WHEN FILTER KEY IS NOT AVAILABLE
    public List<MovieDTO> getMovies() {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream()
                .map(movie -> new MovieDTO(
                        movie.getMovieId(),
                        movie.getTitle(),
                        movie.getBanner(),
                        movie.getGenre(),
                        movie.getLanguage(),
                        movie.getDuration()
                )).toList();
    }


    // SEND THE DATA WHEN USER SELECTS MOVIE TO RESERVATION
    public MovieShowsDTO movieDetails(long movieId) {
        List<MovieTime> movieTimes = movieTimeRepository.findAllByMovieId(movieId);

        long currentCinema = -1; // START FROM THE BEGINNING

        // CREATING TWO LISTS FOR CINEMA TIMESLOTS AND MOVIE CINEMAS
        List<TimeSlotDTO> timeSlotRecordsSet = new ArrayList<>();
        List<ShowTimeDTO> showTimesSet = new ArrayList<>();

        for (int item = 0; item < movieTimes.size(); item++) {
            MovieTime thisItem = movieTimes.get(item);

            Cinema cinema = thisItem.getCinemaId();
            TimeSlot timeSlot = thisItem.getSlotId();

            if (currentCinema == -1 || thisItem.getCinemaId().getId() != currentCinema) {
                // CREATING NEW ShowTimeDTO FOR A CINEMA SHOW TIME
                if (!timeSlotRecordsSet.isEmpty()) {
                    ShowTimeDTO showTime = ShowTimeDTO.builder()
                            .id(currentCinema)
                            .name(cinema.getName())
                            .timeSlots(new ArrayList<>(timeSlotRecordsSet)) // COPY THE LIST
                            .build();
                    showTimesSet.add(showTime);
                }

                // CLEAR THE timeSlotRecordsSet FOR THE NEW CINEMA
                timeSlotRecordsSet.clear();
                currentCinema = thisItem.getCinemaId().getId();
            }

            long thisItemTimeSlotId = timeSlot.getSlotId();
            TimeSlotDTO timeSlotRecord = TimeSlotDTO.builder()
                    .slotId(thisItemTimeSlotId)
                    .startTime(timeSlotRepository.findTimeById(thisItemTimeSlotId))
                    .build();
            timeSlotRecordsSet.add(timeSlotRecord);

            // HANDLE THE LAST CINEMA
            if (item == movieTimes.size() - 1) {
                ShowTimeDTO showTime = ShowTimeDTO.builder()
                        .id(currentCinema)
                        .name(cinema.getName())
                        .timeSlots(new ArrayList<>(timeSlotRecordsSet)) // COPY THE LIST
                        .build();
                showTimesSet.add(showTime);
            }
        }

        // CREATE AND RETURN FINAL OBJECT
        return MovieShowsDTO.builder()
                .movieId(movieId)
                .title(movieRepository.getMovieByMovieId(movieId).getTitle())
                .movieShows(showTimesSet)
                .build();
    }


    // ADD OR UPDATE MOVIES WHEN KAFKA MESSAGE RECEIVED
    public void updateMovieList(KafkaMovieDTO movieReq) throws IOException {

        try {
            MultipartFile image = movieReq.getBanner();    // GET THE FILE FROM DTO

            // GET THE ORIGINAL FILE NAME
            String originalImageName = image.getOriginalFilename();
            assert originalImageName != null;

            // GENERATE UNIQUE FILE NAME USING UUID
            String fileExtension = originalImageName.substring(originalImageName.lastIndexOf("."));
            String uniqueFilename = UUID.randomUUID().toString() + fileExtension;

            // SAVE THE FILE IN UPLOADS FOLDER
            String filePath = Paths.get(FILE_PATH, uniqueFilename).toString();
            Files.copy(image.getInputStream(), Paths.get(FILE_PATH).resolve(uniqueFilename));

            // SAVE OR UPDATE THE RECORD
            movieRepository.save(Movie.builder()
                    .movieId(movieReq.getMovieId())
                    .title(movieReq.getTitle())
                    .banner(filePath)
                    .duration(movieReq.getDuration())
                    .genre(movieReq.getGenre())
                    .language(movieReq.getLanguage())
                    .build()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
