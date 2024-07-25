package com.example.tutorial.auth.api.controllers;

import com.example.tutorial.auth.entities.RefreshToken;
import com.example.tutorial.auth.entities.UserEntity;
import com.example.tutorial.auth.services.AuthService;
import com.example.tutorial.auth.services.JwtService;
import com.example.tutorial.auth.services.RefreshTokenService;
import com.example.tutorial.auth.utils.AuthResponse;
import com.example.tutorial.auth.utils.LoginRequest;
import com.example.tutorial.auth.utils.RefreshTokenRequest;
import com.example.tutorial.auth.utils.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        RefreshToken newRefreshToken = refreshTokenService.verifyRefreshToken((refreshTokenRequest.getRefreshToken()));

        UserEntity user = newRefreshToken.getUser();

        String accessToken = jwtService.generateToken(user);

        return ResponseEntity.ok(AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(newRefreshToken.getToken())
                .build());
    }
}
