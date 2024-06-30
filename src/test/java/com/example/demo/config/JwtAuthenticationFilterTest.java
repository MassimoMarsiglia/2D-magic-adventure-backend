package com.example.demo.config;

import com.example.demo.auth.RegisterRequest;
import com.example.demo.user.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;

import javax.crypto.SecretKey;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class JwtAuthenticationFilterTest {

    @Value("${SECRET_KEY}")
    private String SECRET_KEY;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    String validJwtToken;

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
    public void testFilterWithNoAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/validateToken")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void testFilterWithValidJwtFromHeader() throws Exception {

        // Perform request with JWT in header
        mockMvc.perform(MockMvcRequestBuilders.get("/validateToken")
                        .header("Authorization", "Bearer " + validJwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testFilterWithExpiredJwtFromHeader() throws Exception {
        String invalidJWT = generateSampleExpiredToken("test");

        // Perform request with JWT in header
        assertThrows(ExpiredJwtException.class, () -> {
        mockMvc.perform(MockMvcRequestBuilders.get("/validateToken")
                        .header("Authorization", "Bearer " + invalidJWT)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        });
    }

    @Test
    public void testFilterWithExpiredJwtFromCookie() throws Exception {
        String invalidJWT = generateSampleExpiredToken("test");

        // Perform request with JWT in header
        assertThrows(ExpiredJwtException.class, () -> {
        mockMvc.perform(MockMvcRequestBuilders.get("/validateToken")
                        .cookie(new jakarta.servlet.http.Cookie("jwtToken", invalidJWT))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        });
    }

    @Test
    public void testFilterWithValidJwtFromCookie() throws Exception {

        // Perform request with JWT in cookie
        mockMvc.perform(MockMvcRequestBuilders.get("/validateToken")
                        .cookie(new jakarta.servlet.http.Cookie("jwtToken", validJwtToken))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private String generateSampleExpiredToken(String subject) {

        return Jwts.builder()
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()-200))
                .expiration(new Date(System.currentTimeMillis()-100)) // already expired
                .signWith(getSignInKey())
                .compact();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
