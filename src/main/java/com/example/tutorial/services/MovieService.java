package com.example.tutorial.services;

import com.example.tutorial.dto.MovieDTO;
import com.example.tutorial.entities.MovieEntity;
import com.example.tutorial.exceptions.MovieNotFoundException;
import com.example.tutorial.repositories.MovieRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService implements IMovieService {

    private final MovieRepository movieRepository;
    private final IFileService fileService;

    @Value("${project.poster}")
    private String path;

    @Value("${project.baseurl}")
    private String baseUrl;

    @Override
    public List<MovieDTO> getAllMovies() {
        var movies = movieRepository.findAll();
        return Arrays.stream(movies.toArray()).map(movie -> {
            String posterUrl = baseUrl + "/api/file/" + ((MovieEntity) movie).getPoster();
            return new MovieDTO(
                    ((MovieEntity) movie).getId(),
                    ((MovieEntity) movie).getTitle(),
                    ((MovieEntity) movie).getDirector(),
                    ((MovieEntity) movie).getYear(),
                    ((MovieEntity) movie).getStudio(),
                    ((MovieEntity) movie).getMovieCast(),
                    ((MovieEntity) movie).getPoster(),
                    posterUrl
            );
        }).toList();
    }

    @Override
    public MovieDTO getMovieById(int id) {
        var movie = movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException("Movie not found"));

        String posterUrl = baseUrl + "/api/file/" + movie.getPoster();

        return new MovieDTO(
                movie.getId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getYear(),
                movie.getStudio(),
                movie.getMovieCast(),
                movie.getPoster(),
                posterUrl
        );
    }

    @Override
    @SneakyThrows
    @Builder
    public MovieDTO addMovie(MovieDTO movieDTO, MultipartFile file) {
        String uploadedFileName = fileService.uploadFile(path, file);

        movieDTO.setPoster(uploadedFileName);

        MovieEntity movie = new MovieEntity(
                movieDTO.getId(),
                movieDTO.getTitle(),
                movieDTO.getDirector(),
                movieDTO.getYear(),
                movieDTO.getStudio(),
                movieDTO.getMovieCast(),
                movieDTO.getPoster()
        );

        MovieEntity savedMovie = movieRepository.save(movie);

        String posterUrl = baseUrl + "/api/file/" + uploadedFileName;

        return new MovieDTO(
                savedMovie.getId(),
                savedMovie.getTitle(),
                savedMovie.getDirector(),
                savedMovie.getYear(),
                savedMovie.getStudio(),
                savedMovie.getMovieCast(),
                savedMovie.getPoster(),
                posterUrl
        );
    }

    @Override
    public MovieDTO updateMovie(int id, MovieDTO movieDTO, MultipartFile file) {
        return null;
    }

    @Override
    @SneakyThrows
    public String deleteMovie(int id) {
        MovieEntity movie = movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException("Movie not found"));

        Files.deleteIfExists(Paths.get(path + File.separator + movie.getPoster()));

        movieRepository.delete(movie);

        return "Movie deleted successfully with id: " + id;
    }
}
