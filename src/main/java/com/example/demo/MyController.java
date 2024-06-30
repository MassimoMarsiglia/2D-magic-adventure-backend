package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.LevelData.*;
import com.example.demo.Playerdata.PlayerDataRequest;
import com.example.demo.Playerdata.PlayerDataService;
import com.example.demo.user.UserKeybinds.UserKeyBindsService;
import com.example.demo.user.UserKeybinds.UserKeybindsRequest;

import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class MyController {

    private final UserKeyBindsService userKeyBindsService;

    private final JsonFileReader jsonFileReader;

    private final PlayerDataService playerDataService;

    @GetMapping("/levelData")
    public ResponseEntity<LevelData> getLevelData(@RequestParam("level") String fileName) {
        try {
            LevelData levelData = jsonFileReader.readLevelDataFromFile(fileName);
            System.out.println("hey" + levelData);
            return ResponseEntity.ok(levelData);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    //validates token as all endpoints in this controller are protected by the jwtAuthenticationFilter
    //meaning the request has to pass the filter to get a response code of 200 thefore indicating
    //that the token is valid
    @GetMapping("/validateToken")
    public ResponseEntity<String> validateToken() {
        return ResponseEntity.ok("authenticated");
    }

    @GetMapping("/getKeybinds")
    public ResponseEntity<?> getKeybinds(@CookieValue(name = "jwtToken", required = false) String cookieToken,
                                         @RequestHeader(name = "Authorization", required = false) String bearerToken) {
        
        String token = null;
        // Check if the token is present in the cookie
        if (cookieToken != null) {
            token = cookieToken;
        }
        // If token is not found in cookie, check Authorization header
        if (token == null && bearerToken != null && bearerToken.startsWith("Bearer ")) {
            token = bearerToken.substring(7); // Extract token excluding "Bearer "
        }
        // If token is still null, handle accordingly (return an error, for example)
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return userKeyBindsService.getKeyBindings(token);
    }
    @PostMapping("/saveKeybinds")
    public ResponseEntity<String> saveKeybinds(@CookieValue(name = "jwtToken", required = false) String cookieToken,
                                               @RequestHeader(name = "Authorization", required = false) String bearerToken,
                                               @RequestBody UserKeybindsRequest keybindsDTO) {
        String token = null;
        // Check if the token is present in the cookie
        if (cookieToken != null) {
            token = cookieToken;
        }
        // If token is not found in cookie, check Authorization header
        if (token == null && bearerToken != null && bearerToken.startsWith("Bearer ")) {
            token = bearerToken.substring(7); // Extract token excluding "Bearer "
        }
        // If token is still null, handle accordingly (return an error, for example)
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return userKeyBindsService.saveUserKeybinds(token, keybindsDTO);
    }
    @PostMapping("/savePlayerData")
    public ResponseEntity<String> savePlayerData(@CookieValue(name = "jwtToken", required = false) String cookieToken,
                                               @RequestHeader(name = "Authorization", required = false) String bearerToken,
                                               @RequestBody PlayerDataRequest playerData) {
        String token = null;
        // Check if the token is present in the cookie
        if (cookieToken != null) {
            token = cookieToken;
        }
        // If token is not found in cookie, check Authorization header
        if (token == null && bearerToken != null && bearerToken.startsWith("Bearer ")) {
            token = bearerToken.substring(7); // Extract token excluding "Bearer "
        }
        // If token is still null, handle accordingly (return an error, for example)
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return playerDataService.savePlayerData(token, playerData);
    }
    @GetMapping("/getPlayerData")
    public ResponseEntity<?> getPlayerData(@CookieValue(name = "jwtToken", required = false) String cookieToken,
                                         @RequestHeader(name = "Authorization", required = false) String bearerToken) {
        
        String token = null;
        // Check if the token is present in the cookie
        if (cookieToken != null) {
            token = cookieToken;
        }
        // If token is not found in cookie, check Authorization header
        if (token == null && bearerToken != null && bearerToken.startsWith("Bearer ")) {
            token = bearerToken.substring(7); // Extract token excluding "Bearer "
        }
        // If token is still null, handle accordingly (return an error, for example)
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return playerDataService.getPlayerData(token);
    }
}

