package com.example.demo.playerData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import com.example.demo.Playerdata.PlayerData;
import com.example.demo.Playerdata.PlayerDataRepository;
import com.example.demo.Playerdata.PlayerDataRequest;
import com.example.demo.Playerdata.PlayerDataService;
import com.example.demo.auth.RegisterRequest;
import com.example.demo.config.JwtService;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PlayerDataServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlayerDataRepository playerDataRepository;

    private String validJwtToken;

    @BeforeEach
    public void setup() throws JsonProcessingException, Exception {
        // Clear existing data
        userRepository.deleteAll();

        // Register test user
        RegisterRequest registerRequest = new RegisterRequest("John Doe", "john@example.com", "password");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        JsonNode responseJson = objectMapper.readTree(responseContent);
        validJwtToken = responseJson.get("token").asText();
    }


    @Test
    public void testSavePlayerData_cookie() throws Exception {
        PlayerDataRequest request = new PlayerDataRequest();
        request.setCurrentLevel("Level2");

        // Perform POST request
        MvcResult result = mockMvc.perform(post("/savePlayerData")
                .cookie(new jakarta.servlet.http.Cookie("jwtToken", validJwtToken))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        String email = jwtService.extractEmail(validJwtToken);

        Optional<User> userOptional = userRepository.findByUsername(email);

        
        User user = userOptional.get();

        String currentLevel = user.getPlayerData().getCurrentLevel();

        assertEquals("Playerdata saved successfully", content);
        assertEquals("Level2", currentLevel);
    }

    @Test
    public void testSavePlayerData_bearer() throws Exception {
        PlayerDataRequest request = new PlayerDataRequest();
        request.setCurrentLevel("Level2");

        // Perform POST request
        MvcResult result = mockMvc.perform(post("/savePlayerData")
                .header("Authorization", "Bearer " + validJwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        

        String email = jwtService.extractEmail(validJwtToken);

        Optional<User> userOptional = userRepository.findByUsername(email);

        
        User user = userOptional.get();

        String currentLevel = user.getPlayerData().getCurrentLevel();

        assertEquals("Playerdata saved successfully", content);
        assertEquals("Level2", currentLevel);
    }

    @Test
    public void testGetPlayerData_bearer() throws Exception {
        String email = jwtService.extractEmail(validJwtToken);

        Optional<User> userOptional = userRepository.findByUsername(email);

        
        User user = userOptional.get();


        PlayerData playerData = user.getPlayerData();
        if (playerData == null) {
            // Initialize PlayerData if it's null (based on your application logic)
            playerData = new PlayerData();
            playerData.setUser(user);
            user.setPlayerData(playerData); // Set PlayerData back to User
        }

        playerData.setCurrentLevel("level5");

        // Save or update User and PlayerData entities if needed
        userRepository.save(user);

        // Perform GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/getPlayerData")
            .header("Authorization", "Bearer " + validJwtToken)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.currentLevel").value(playerData.getCurrentLevel()));
        
    }

    @Test
    public void testGetPlayerData_cookie() throws Exception {
        String email = jwtService.extractEmail(validJwtToken);

        Optional<User> userOptional = userRepository.findByUsername(email);

        
        User user = userOptional.get();


        PlayerData playerData = user.getPlayerData();
        if (playerData == null) {
            // Initialize PlayerData if it's null (based on your application logic)
            playerData = new PlayerData();
            playerData.setUser(user);
            user.setPlayerData(playerData); // Set PlayerData back to User
        }

        playerData.setCurrentLevel("level5");

        // Save or update User and PlayerData entities if needed
        userRepository.save(user);

        // Perform GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/getPlayerData")
            .cookie(new jakarta.servlet.http.Cookie("jwtToken", validJwtToken))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.currentLevel").value(playerData.getCurrentLevel()));
        
    }
}
