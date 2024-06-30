package com.example.demo.auth;

import com.example.demo.user.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class AuthenticationControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private UserRepository userRepository;

        @BeforeEach
        public void setup() throws JsonProcessingException, Exception {
                // You can setup test data or mock behaviors here
                // Clear existing data
                userRepository.deleteAll();

                RegisterRequest request = new RegisterRequest();
                request.setEmail("testuser@gmail.com");
                request.setName("thomas");
                request.setPassword("password");
                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                        .andDo(print())
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists());
                
        }

        @Test
        public void testRegister_success() throws Exception {
                RegisterRequest request = new RegisterRequest();
                request.setEmail("testuser1@gmail.com");
                request.setPassword("password");

                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                        .andDo(print())
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists());
        }

        @Test
        public void testAuthenticate_success() throws Exception {

                // Now authenticate with registered user
                AuthenticationRequest authenticationRequest = new AuthenticationRequest();
                authenticationRequest.setEmail("testuser@gmail.com");
                authenticationRequest.setPassword("password");

                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/authenticate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(authenticationRequest)))
                        .andDo(print())
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists());
        }

        @Test
        public void testAuthenticate_failure_1() throws Exception {
                // Now authenticate with registered user
                AuthenticationRequest authenticationRequest = new AuthenticationRequest();
                authenticationRequest.setEmail("testuser@gmail.com");
                authenticationRequest.setPassword("passwor"); //wrong password

                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/authenticate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(authenticationRequest)))
                        .andDo(print())
                        .andExpect(MockMvcResultMatchers.status().isForbidden());
        }
        @Test
        public void testAuthenticate_failure_2() throws Exception {
                // Now authenticate with registered user
                AuthenticationRequest authenticationRequest = new AuthenticationRequest();
                authenticationRequest.setEmail("testusr@gmail.com"); //wrong email
                authenticationRequest.setPassword("password");

                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/authenticate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(authenticationRequest)))
                        .andDo(print())
                        .andExpect(MockMvcResultMatchers.status().isForbidden());
        }
}
