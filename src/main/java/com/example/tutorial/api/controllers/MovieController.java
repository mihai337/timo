package com.example.tutorial.api.controllers;

import com.example.tutorial.dto.MovieDTO;
import com.example.tutorial.services.IMovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/movies")
public class MovieController {

    private final IMovieService movieService;

    @PostMapping("/add-movie")
    public ResponseEntity<MovieDTO> addMovie(@RequestParam MultipartFile file,@RequestParam String movieDTO){
        MovieDTO dto = convertToMovieDTO(movieDTO);
        return new ResponseEntity<>(movieService.addMovie(dto, file), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable int id){
        return new ResponseEntity<>(movieService.getMovieById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MovieDTO>> getAllMovies(){
        return new ResponseEntity<>(movieService.getAllMovies(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable int id){
        return new ResponseEntity<>(movieService.deleteMovie(id), HttpStatus.OK);
    }

    @SneakyThrows
    private MovieDTO convertToMovieDTO(String movieDTO) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(movieDTO, MovieDTO.class);
    }
}

//json file
// {
//     "id": 1,
//     "title": "The Shawshank Redemption",
//     "director": "Frank Darabont",
//     "year": 1994,
//     "studio": "Castle Rock Entertainment",
//     "movieCast": ["Tim Robbins", "Morgan Freeman", "Bob Gunton"],
//     "poster": "shawshank_redemption.jpg",
//     "posterUrl": "url"
// }
