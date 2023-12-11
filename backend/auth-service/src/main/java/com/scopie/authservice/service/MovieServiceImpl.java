package com.scopie.authservice.service;

import com.scopie.authservice.dto.MovieDTO;
import com.scopie.authservice.kafka.dto.KafkaMovieDTO;
import com.scopie.authservice.entity.Movie;
import com.scopie.authservice.repository.MovieRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${upload.path}")
    private String FILE_PATH;

    // GET MOVIES WHEN FILTER KEY IS AVAILABLE
    public List<Movie> getMovies(String filter) {
        return movieRepository.findAllWithFilter(filter);
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

    // GET THE MOVIE DETAILS WHEN USER CLICK ON SOME MOVIE
    public Movie movieDetails(Long movieId) {
        return movieRepository.getMovieByMovieId(movieId);
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
