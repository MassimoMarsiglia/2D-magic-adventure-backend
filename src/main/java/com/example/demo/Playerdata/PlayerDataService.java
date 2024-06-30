package com.example.demo.Playerdata;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.config.JwtService;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class PlayerDataService {

    private final JwtService jwtService;

    private final UserRepository userRepository;

    private final PlayerDataRepository playerDataRepository;

    public ResponseEntity<String> savePlayerData(String token, PlayerDataRequest playerDataRequest) {
        String email = jwtService.extractEmail(token);
        Optional<User> userOptional = userRepository.findByUsername(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Optional<PlayerData> playerDataOptional = playerDataRepository.findByUser_Id(user.getId());
            PlayerData playerData;
            if (playerDataOptional.isPresent()) {
                playerData = playerDataOptional.get();
            } else {
                playerData = new PlayerData();
                playerData.setUser(user);
            }

            // Set the updated keybinds
            playerData.setCurrentLevel(playerDataRequest.getCurrentLevel());

            // Save or update UserKeybinds entity
            playerDataRepository.save(playerData);

            return ResponseEntity.ok("Playerdata saved successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    public ResponseEntity<?> getPlayerData(String token) {
        if (token != null) {
            String email = jwtService.extractEmail(token);
            Optional<User> userOptional = userRepository.findByUsername(email);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                // Process the user object as needed
                Optional<PlayerData> playerDataOptional = playerDataRepository.findByUser_Id(user.getId());
                if (playerDataOptional.isPresent()) {
                    PlayerData playerData = playerDataOptional.get();
                    // Construct a response object with desired fields
                    // Example: Constructing a JSON response
                    return ResponseEntity.ok().body(Map.of(
                        "currentLevel", playerData.getCurrentLevel()
                    ));
                } else {
                    // Handle case where user keybinds are not found
                    return ResponseEntity.notFound().build();
                }
            } else {
                // Handle case where user with the given email is not found
                return ResponseEntity.notFound().build();
            }
        }
        else {
            // Handle case where token is null (cookie not found)
            return ResponseEntity.badRequest().body("JWT Token not found in cookie");
        }
    }
    
}
