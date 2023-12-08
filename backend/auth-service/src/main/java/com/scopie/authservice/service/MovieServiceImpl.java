package com.scopie.authservice.service;

import com.scopie.authservice.kafka.dto.KafkaMovieDTO;
import com.scopie.authservice.entity.Movie;
import com.scopie.authservice.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService{

    @Autowired
    private MovieRepository movieRepository;

    @Value("${upload.path}")
    private String FILE_PATH;

    // GET MOVIES WHEN FILTER KEY IS AVAILABLE
    public List<Movie> getMovies(String filter) {
        return movieRepository.findAllWithFilter(filter);
    }

    // GET MOVIES WHEN FILTER KEY IS NOT AVAILABLE
    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }

    // GET THE MOVIE DETAILS WHEN USER CLICK ON SOME MOVIE
    public Movie movieDetails(Long movieId) {
        return movieRepository.getMovieByMovieId(movieId);
    }

    // ADD OR UPDATE MOVIES
    public void updateMovieList(KafkaMovieDTO movieReq) throws IOException {

        MultipartFile image = movieReq.getBanner();    // GET IMAGE FROM DATASET

        String imageName = UUID.randomUUID().toString()+image.getContentType();     // SET A NAME FOR THE FILE
        String imagePath = FILE_PATH+imageName;

        // TODO: NEED TO CHANGE THE FILE NAME BEFORE STORE IT ON UPLOADS FOLDER

        movieRepository.save(Movie.builder()
                .movieId(movieReq.getMovieId())
                .title(movieReq.getTitle())
                .banner(imagePath)
                .duration(movieReq.getDuration())
                .genre(movieReq.getGenre())
                .language(movieReq.getLanguage())
                .build()
        );

        // TRANSFER THE FILE TO THE UPLOADS FOLDER
        image.transferTo(new File(FILE_PATH));
    }
}
