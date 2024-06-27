package com.example.demo.user;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.config.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserKeyBindsService {

    private final JwtService jwtService;

    private final UserRepository userRepository;

    private final UserKeybindsRepository userKeybindsRepository;

    public ResponseEntity<String> saveUserKeybinds(String token, UserKeybindsRequest keybindsDTO) {
        String email = jwtService.extractEmail(token);
        Optional<User> userOptional = userRepository.findByUsername(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Optional<UserKeybinds> userKeybindsOptional = userKeybindsRepository.findByUser_Id(user.getId());
            UserKeybinds userKeybinds;
            if (userKeybindsOptional.isPresent()) {
                userKeybinds = userKeybindsOptional.get();
            } else {
                userKeybinds = new UserKeybinds();
                userKeybinds.setUser(user);
            }

            // Set the updated keybinds
            userKeybinds.setMoveUp(keybindsDTO.getMoveUp());
            userKeybinds.setMoveDown(keybindsDTO.getMoveDown());
            userKeybinds.setMoveLeft(keybindsDTO.getMoveLeft());
            userKeybinds.setMoveRight(keybindsDTO.getMoveRight());
            userKeybinds.setShoot(keybindsDTO.getShoot());
            userKeybinds.setSettings(keybindsDTO.getSettings());

            // Save or update UserKeybinds entity
            userKeybindsRepository.save(userKeybinds);

            return ResponseEntity.ok("Keybinds saved successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    public ResponseEntity<?> getKeyBindings(String token) {
        if (token != null) {
            String email = jwtService.extractEmail(token);
            Optional<User> userOptional = userRepository.findByUsername(email);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                // Process the user object as needed
                Optional<UserKeybinds> userKeybindsOptional = userKeybindsRepository.findByUser_Id(user.getId());
                if (userKeybindsOptional.isPresent()) {
                    UserKeybinds userKeybinds = userKeybindsOptional.get();
                    // Construct a response object with desired fields
                    // Example: Constructing a JSON response
                    return ResponseEntity.ok().body(Map.of(
                        "moveUp", userKeybinds.getMoveUp(),
                        "moveDown", userKeybinds.getMoveDown(),
                        "moveLeft", userKeybinds.getMoveLeft(),
                        "moveRight", userKeybinds.getMoveRight(),
                        "shoot", userKeybinds.getShoot(),
                        "settings", userKeybinds.getSettings()
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

