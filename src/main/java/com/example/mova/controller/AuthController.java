package com.example.mova.controller;

import com.example.mova.config.TokenProvider;
import com.example.mova.dto.JwtToken;
import com.example.mova.dto.RefreshTokenRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final TokenProvider tokenProvider;
    private final UserDetailsService userDetailsService;

    @PostMapping("/refresh")
    public ResponseEntity<JwtToken> refreshToken(@RequestBody RefreshTokenRequest request) {
        String rt = request.getRefreshToken();
        if (!tokenProvider.validateToken(rt)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = tokenProvider.getUsernameFromToken(rt);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );

        JwtToken newToken = tokenProvider.generateToken(auth);
        return ResponseEntity.ok(newToken);
    }
}
