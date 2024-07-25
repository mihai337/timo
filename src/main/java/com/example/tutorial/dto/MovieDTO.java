package com.example.tutorial.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {

    private int id;
    private String title;
    private String director;
    private int year;
    private String studio;
    private Set<String> movieCast;
    private String poster;
    private String posterUrl;
}
