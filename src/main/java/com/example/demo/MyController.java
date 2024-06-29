package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.LevelData.*;
import com.example.demo.user.UserKeyBindsService;
import com.example.demo.user.UserKeybindsRequest;

import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class MyController {

    private final UserKeyBindsService userKeyBindsService;

    private final JsonFileReader jsonFileReader;

    @GetMapping("/levelData")
    public ResponseEntity<LevelData> getLevelData(@RequestParam("level") String fileName) {
        try {
            LevelData levelData = jsonFileReader.readLevelDataFromFile(fileName);
            return ResponseEntity.ok(levelData);
        } catch (IOException e) {
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
    public ResponseEntity<?> getKeybinds(@CookieValue(name = "jwtToken") String token) {
        return userKeyBindsService.getKeyBindings(token);
    }
    @PostMapping("/saveKeybinds")
    public ResponseEntity<String> saveKeybinds(@CookieValue(name = "jwtToken") String token,
                                               @RequestBody UserKeybindsRequest keybindsDTO) {
    return userKeyBindsService.saveUserKeybinds(token, keybindsDTO);
    }
}

