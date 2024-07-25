package com.example.tutorial.services;

import com.example.tutorial.dto.MovieDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IMovieService {
    List<MovieDTO> getAllMovies();

    MovieDTO getMovieById(int id);

    MovieDTO addMovie(MovieDTO movieDTO, MultipartFile file);

    MovieDTO updateMovie(int id, MovieDTO movieDTO, MultipartFile file);

    String deleteMovie(int id);
}
