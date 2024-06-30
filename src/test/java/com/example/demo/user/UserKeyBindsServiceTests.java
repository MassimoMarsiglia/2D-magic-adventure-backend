package com.example.demo.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demo.auth.RegisterRequest;
import com.example.demo.user.UserKeybinds.UserKeybinds;
import com.example.demo.user.UserKeybinds.UserKeybindsRepository;
import com.example.demo.user.UserKeybinds.UserKeybindsRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserKeyBindsServiceTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserKeybindsRepository userKeybindsRepository;

    @Autowired
    private ObjectMapper objectMapper;

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
    public void testSaveUserKeybinds_bearer() throws Exception {

        UserKeybindsRequest keybindsDTO = new UserKeybindsRequest();
        keybindsDTO.setMoveUp("w");
        keybindsDTO.setMoveDown("s");
        keybindsDTO.setMoveLeft("a");
        keybindsDTO.setMoveRight("d");
        keybindsDTO.setShoot("f");
        keybindsDTO.setSettings("p");

        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/saveKeybinds")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + validJwtToken)
                .content(objectMapper.writeValueAsString(keybindsDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Keybinds saved successfully"));

        // Verify in the repository
        Optional<UserKeybinds> savedKeybindsOptional = userKeybindsRepository.findByUser_Id(userRepository.findByUsername("john@example.com").get().getId());
        assertTrue(savedKeybindsOptional.isPresent());
        UserKeybinds savedKeybinds = savedKeybindsOptional.get();
        assertEquals("w", savedKeybinds.getMoveUp());
        assertEquals("s", savedKeybinds.getMoveDown());
        assertEquals("a", savedKeybinds.getMoveLeft());
        assertEquals("d", savedKeybinds.getMoveRight());
        assertEquals("f", savedKeybinds.getShoot());
        assertEquals("p", savedKeybinds.getSettings());
    }

    @Test
    public void testSaveUserKeybinds_noAuth() throws Exception {

        UserKeybindsRequest keybindsDTO = new UserKeybindsRequest();
        keybindsDTO.setMoveUp("w");
        keybindsDTO.setMoveDown("s");
        keybindsDTO.setMoveLeft("a");
        keybindsDTO.setMoveRight("d");
        keybindsDTO.setShoot("f");
        keybindsDTO.setSettings("p");

        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/saveKeybinds")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(keybindsDTO)))
                .andExpect(status().isForbidden());
    }


    @Test
    public void testSaveUserKeybinds_cookie() throws Exception {

        UserKeybindsRequest keybindsDTO = new UserKeybindsRequest();
        keybindsDTO.setMoveUp("w");
        keybindsDTO.setMoveDown("s");
        keybindsDTO.setMoveLeft("a");
        keybindsDTO.setMoveRight("d");
        keybindsDTO.setShoot("f");
        keybindsDTO.setSettings("p");

        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/saveKeybinds")
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(new jakarta.servlet.http.Cookie("jwtToken", validJwtToken))
                .content(objectMapper.writeValueAsString(keybindsDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Keybinds saved successfully"));

        // Verify in the repository
        Optional<UserKeybinds> savedKeybindsOptional = userKeybindsRepository.findByUser_Id(userRepository.findByUsername("john@example.com").get().getId());
        assertTrue(savedKeybindsOptional.isPresent());
        UserKeybinds savedKeybinds = savedKeybindsOptional.get();
        assertEquals("w", savedKeybinds.getMoveUp());
        assertEquals("s", savedKeybinds.getMoveDown());
        assertEquals("a", savedKeybinds.getMoveLeft());
        assertEquals("d", savedKeybinds.getMoveRight());
        assertEquals("f", savedKeybinds.getShoot());
        assertEquals("p", savedKeybinds.getSettings());
    }

    @Test
    public void testGetKeyBindings_bearer() throws Exception {
        // Given

        UserKeybinds keybinds = new UserKeybinds();
        keybinds.setUser(userRepository.findByUsername("john@example.com").get());
        keybinds.setMoveUp("w");
        keybinds.setMoveDown("s");
        keybinds.setMoveLeft("a");
        keybinds.setMoveRight("d");
        keybinds.setShoot("f");
        keybinds.setSettings("p");
        userKeybindsRepository.save(keybinds);

        // When
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/getKeybinds")
                .header("Authorization", "Bearer " + validJwtToken));
        // Then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.moveUp").value("w"))
                .andExpect(jsonPath("$.moveDown").value("s"))
                .andExpect(jsonPath("$.moveLeft").value("a"))
                .andExpect(jsonPath("$.moveRight").value("d"))
                .andExpect(jsonPath("$.shoot").value("f"))
                .andExpect(jsonPath("$.settings").value("p"));
    }
    @Test
    public void testGetKeyBindings_cookie() throws Exception {
        // Given

        UserKeybinds keybinds = new UserKeybinds();
        keybinds.setUser(userRepository.findByUsername("john@example.com").get());
        keybinds.setMoveUp("w");
        keybinds.setMoveDown("s");
        keybinds.setMoveLeft("a");
        keybinds.setMoveRight("d");
        keybinds.setShoot("f");
        keybinds.setSettings("p");
        userKeybindsRepository.save(keybinds);

        // When
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/getKeybinds")
        .cookie(new jakarta.servlet.http.Cookie("jwtToken", validJwtToken)));
        // Then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.moveUp").value("w"))
                .andExpect(jsonPath("$.moveDown").value("s"))
                .andExpect(jsonPath("$.moveLeft").value("a"))
                .andExpect(jsonPath("$.moveRight").value("d"))
                .andExpect(jsonPath("$.shoot").value("f"))
                .andExpect(jsonPath("$.settings").value("p"));
    }
    @Test
    public void testGetKeyBindings_noAuth() throws Exception {
        // Given

        UserKeybinds keybinds = new UserKeybinds();
        keybinds.setUser(userRepository.findByUsername("john@example.com").get());
        keybinds.setMoveUp("w");
        keybinds.setMoveDown("s");
        keybinds.setMoveLeft("a");
        keybinds.setMoveRight("d");
        keybinds.setShoot("f");
        keybinds.setSettings("p");
        userKeybindsRepository.save(keybinds);

        // When
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/getKeybinds"));
        // Then
        resultActions.andExpect(status().isForbidden());
    }
}
