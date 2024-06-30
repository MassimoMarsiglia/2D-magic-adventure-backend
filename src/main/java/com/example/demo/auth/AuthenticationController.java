package com.example.demo.auth;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseCookie;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
        @RequestBody RegisterRequest request) {
        AuthenticationResponse authResponse = service.register(request);
        
        ResponseCookie cookie = ResponseCookie.from("jwtToken", authResponse.getToken())
                .path("/") // Specify the path where the cookie is valid, "/" means the whole application
                .maxAge(24 * 60 * 60) // Set the expiration time in seconds
                .httpOnly(true)
                .secure(true) // uncomment to deploy
                .sameSite("None") //sameSite set to "None" because render free tier doesnt let you host frontend and backend
                .build();                  //on the same domain without purchasing and setting up your own domain. In an actual deployment 
                                           //with a proper domain setup sameSite should be set to strict for security reasons
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok()
                .headers(headers)
                .body(authResponse);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
        @RequestBody AuthenticationRequest request) {
        AuthenticationResponse authResponse = service.authenticate(request);

        ResponseCookie cookie = ResponseCookie.from("jwtToken", authResponse.getToken())
                .path("/") // Specify the path where the cookie is valid, "/" means the whole application
                .maxAge(24 * 60 * 60) // Set the expiration time in seconds
                .httpOnly(true)
                .secure(true) // uncomment to deploy
                .sameSite("None") //sameSite set to "None" because render free tier doesnt let you host frontend and backend
                .build();                  //on the same domain without purchasing and setting up your own domain in an actual deployment 
                                           //with a proper domain setup sameSite should be set to strict for security reasons
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok()
                .headers(headers)
                .body(authResponse);
    }
}
