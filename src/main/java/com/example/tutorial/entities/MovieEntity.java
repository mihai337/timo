package com.example.tutorial.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "movies")
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "director", nullable = false)
    private String director;

    @Column(name = "year", nullable = false)
    private int year;

    @Column(name = "studio", nullable = false)
    private String studio;

    @ElementCollection
    @Column(name = "movie_cast", nullable = false)
    private Set<String> movieCast;

    @Column(name = "poster", nullable = false)
    private String poster;

}
