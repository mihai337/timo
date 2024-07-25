package com.example.tutorial.auth.services;

import com.example.tutorial.auth.entities.RefreshToken;
import com.example.tutorial.auth.entities.UserEntity;
import com.example.tutorial.auth.repositories.RefreshTokenRepository;
import com.example.tutorial.auth.repositories.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final UserRepository userRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken createRefreshToken(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        RefreshToken refreshToken = user.getRefreshToken();

        if(refreshToken == null){
            refreshToken = RefreshToken.builder()
                    .token(UUID.randomUUID().toString())
                    .expirationTime(Instant.now().plusMillis(86400000))
                    .user(user)
                    .build();

            refreshTokenRepository.save(refreshToken);
        }

        return refreshToken;
    }

    public RefreshToken verifyRefreshToken(String token) {
        RefreshToken reftoken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));

        if(reftoken.getExpirationTime().isBefore(Instant.now())){
            throw new RuntimeException("Refresh token has expired");
        }

        return reftoken;
    }
}
