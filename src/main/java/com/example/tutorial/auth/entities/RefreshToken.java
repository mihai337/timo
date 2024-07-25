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
    @NotBlank(message = "Token should not be blank")
    private String token;

    @Column(name = "expiration_time", nullable = false)
    @NotBlank(message = "Expiration time should not be blank")
    private Instant expirationTime;

    @OneToOne
    @NotBlank(message = "User should not be blank")
    private UserEntity user;
}
