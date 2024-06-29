// package com.example.demo.auth;

// import com.example.demo.user.Role;
// import com.example.demo.user.User;
// import com.example.demo.user.UserRepository;
// import com.fasterxml.jackson.core.JsonProcessingException;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
// import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
// import org.springframework.transaction.annotation.Transactional;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotEquals;

// @SpringBootTest
// @AutoConfigureMockMvc
// // @AutoConfigureTestDatabase
// public class AuthenticationServiceTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @Autowired
//     private UserRepository userRepository;

//     @Autowired
//     private ObjectMapper objectMapper;

//     @Autowired
//     private AuthenticationService authenticationService;

//     @BeforeEach
//     public void setUp() throws JsonProcessingException, Exception {
//         // Clear existing data
//         userRepository.deleteAll();

//         //then register test user
//         RegisterRequest registerRequest = new RegisterRequest("John Doe", "john@example.com", "password");
//         mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
//                         .contentType(MediaType.APPLICATION_JSON)
//                         .content(objectMapper.writeValueAsString(registerRequest)))
//                 .andExpect(MockMvcResultMatchers.status().isOk());
//     }

//     @Test
//     public void testAuthenticate_success() throws Exception {
//         // Authenticate with registered user credentials
//         AuthenticationRequest authenticationRequest = new AuthenticationRequest("john@example.com", "password");
//         mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/authenticate")
//                         .contentType(MediaType.APPLICATION_JSON)
//                         .content(objectMapper.writeValueAsString(authenticationRequest)))
//                 .andExpect(MockMvcResultMatchers.status().isOk())
//                 .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists());
//     }
    
//     @Test
//     public void testAuthenticate_failure_1() throws Exception {
//         // Authenticate with registered user credentials
//         AuthenticationRequest authenticationRequest = new AuthenticationRequest("john@example.com", "passwor"); //wrong password
//         mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/authenticate")
//                         .contentType(MediaType.APPLICATION_JSON)
//                         .content(objectMapper.writeValueAsString(authenticationRequest)))
//                 .andExpect(MockMvcResultMatchers.status().isForbidden());
//     }

//     @Test
//     public void testAuthenticate_failure_2() throws Exception {
//         // Authenticate with registered user credentials
//         AuthenticationRequest authenticationRequest = new AuthenticationRequest("john@example.co", "password"); //wrong email
//         mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/authenticate")
//                         .contentType(MediaType.APPLICATION_JSON)
//                         .content(objectMapper.writeValueAsString(authenticationRequest)))
//                 .andExpect(MockMvcResultMatchers.status().isForbidden());
//     }

//     @Test
//     public void testRegister() throws Exception {
//         RegisterRequest registerRequest = new RegisterRequest("Jane Doe", "jane@example.com", "password");

//         authenticationService.register(registerRequest);

//         // Verify user is saved in the database
//         User savedUser = userRepository.findByUsername("jane@example.com").orElse(null);
//         assertNotEquals(savedUser, null);
//         assertEquals(savedUser.getName(), "Jane Doe");
//         assertEquals(savedUser.getRole(), Role.USER);
//     }
// }
