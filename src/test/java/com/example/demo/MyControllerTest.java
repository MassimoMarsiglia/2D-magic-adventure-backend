// package com.example.demo;

// import com.example.demo.auth.*;
// import com.example.demo.config.*;
// import com.example.demo.user.UserRepository;
// import com.example.demo.user.UserKeybinds.UserKeyBindsService;
// import com.example.demo.user.UserKeybinds.UserKeybindsRequest;
// import com.fasterxml.jackson.core.JsonProcessingException;
// import com.fasterxml.jackson.databind.JsonNode;
// import com.fasterxml.jackson.databind.ObjectMapper;

// import jakarta.servlet.http.Cookie;

// import com.example.demo.LevelData.*; // Ensure this import is present

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mockito;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.test.context.ActiveProfiles;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.MvcResult;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
// import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

// import java.io.IOException;

// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.doReturn;
// import static org.mockito.Mockito.when;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// @ActiveProfiles("test")
// @SpringBootTest
// public class MyControllerTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @Autowired
//     private UserKeyBindsService userKeyBindsService;

//     @Autowired
//     private JwtAuthenticationFilter jwtAuthenticationFilter;

//     @Autowired
//     private JsonFileReader jsonFileReader;

//     @Autowired
//     private UserRepository userRepository;

//     @Autowired
//     private ObjectMapper objectMapper;

//     private String validJwtToken;

//     @BeforeEach
//     public void setup() throws JsonProcessingException, Exception {
//         // Clear existing data
//         userRepository.deleteAll();

//         // Register test user
//         RegisterRequest registerRequest = new RegisterRequest("John Doe", "john@example.com", "password");
//         MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
//                         .contentType(MediaType.APPLICATION_JSON)
//                         .content(objectMapper.writeValueAsString(registerRequest)))
//                 .andExpect(MockMvcResultMatchers.status().isOk())
//                 .andReturn();

//         String responseContent = result.getResponse().getContentAsString();
//         JsonNode responseJson = objectMapper.readTree(responseContent);
//         validJwtToken = responseJson.get("token").asText();
//     }

//     @Test
//     public void testGetLevelData_success() throws Exception {
//         mockMvc.perform(MockMvcRequestBuilders.get("/levelData")
//                     .param("level", "level1.json")
//                     .header("Authorization", "Bearer " + validJwtToken))
//             .andExpect(status().isOk())
//             .andExpect(jsonPath("$.yourField").value("yourValue")); // Adjust jsonPath according to your LevelData fields
//     }

//     @Test
//     public void testGetLevelData_failure() throws Exception {
//         when(jsonFileReader.readLevelDataFromFile("level1.json")).thenThrow(new IOException());

//         mockMvc.perform(MockMvcRequestBuilders.get("/levelData")
//                         .param("level", "level1.json"))
//                 .andExpect(status().isInternalServerError());
//     }

//     @Test
//     public void testValidateToken_success() throws Exception {
//         mockMvc.perform(MockMvcRequestBuilders.get("/validateToken"))
//                 .andExpect(status().isOk())
//                 .andExpect(content().string("authenticated"));
//     }

//     @Test
//     public void testGetKeybinds_success() throws Exception {
//         doReturn(ResponseEntity.ok("keybinds")).when(userKeyBindsService).getKeyBindings(validJwtToken);

//         mockMvc.perform(MockMvcRequestBuilders.get("/getKeybinds")
//                         .cookie(new Cookie("jwtToken", validJwtToken)))
//                 .andExpect(status().isOk())
//                 .andExpect(content().string("keybinds"));
//     }

//     @Test
//     public void testSaveKeybinds_success() throws Exception {
//         UserKeybindsRequest request = new UserKeybindsRequest();
//         when(userKeyBindsService.saveUserKeybinds(any(String.class), any(UserKeybindsRequest.class)))
//                 .thenReturn(ResponseEntity.ok("saved"));

//         mockMvc.perform(MockMvcRequestBuilders.post("/saveKeybinds")
//                         .cookie(new Cookie("jwtToken", validJwtToken))
//                         .contentType(MediaType.APPLICATION_JSON)
//                         .content("{\"yourField\":\"yourValue\"}")) // Adjust content according to your UserKeybindsRequest fields
//                 .andExpect(status().isOk())
//                 .andExpect(content().string("saved"));
//     }
// }
