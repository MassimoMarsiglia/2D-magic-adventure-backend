package com.example.demo.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
        @RequestBody RegisterRequest request,
        HttpServletResponse response) {
        // return ResponseEntity.ok(service.register(request));
        AuthenticationResponse authResponse = service.register(request);
        
        Cookie cookie = new Cookie("jwtToken", authResponse.getToken());
        cookie.setPath("/"); // Specify the path where the cookie is valid, "/" means the whole application
        cookie.setMaxAge(24 * 60 * 60); // Set the expiration time in seconds
        cookie.setHttpOnly(true);
        cookie.setSecure(true); //disabled for development
        response.addCookie(cookie);

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(
        @RequestBody AuthenticationRequest request,
        HttpServletResponse response) {
        // return ResponseEntity.ok(service.authenticate(request));
        AuthenticationResponse authResponse = service.authenticate(request);

        Cookie cookie = new Cookie("jwtToken", authResponse.getToken());
        cookie.setPath("/"); // Specify the path where the cookie is valid, "/" means the whole application
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setHttpOnly(true); // Set the expiration time in seconds
        cookie.setSecure(true); //disabled for development
        response.addCookie(cookie);

        return ResponseEntity.ok(authResponse);
    }
}
