package com.example.tutorial.auth.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int tokenId;

    @Column(name = "token", nullable = false, length = 500)
    private String token;

    @Column(name = "expiration_time", nullable = false)
    private Instant expirationTime;

    @OneToOne
    private UserEntity user;
}
