// package com.example.demo;

// import com.example.demo.auth.*;
// import com.example.demo.config.*;
// import com.example.demo.user.UserKeybinds.UserKeyBindsService;
// import com.example.demo.user.UserKeybinds.UserKeybindsRequest;

// import jakarta.servlet.http.Cookie;

// import com.example.demo.LevelData.*; // Ensure this import is present

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mockito;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

// import java.io.IOException;

// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.doReturn;
// import static org.mockito.Mockito.when;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// @WebMvcTest(MyController.class)
// public class MyControllerTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @MockBean
//     private UserKeyBindsService userKeyBindsService;

//     @MockBean
//     private JwtAuthenticationFilter jwtAuthenticationFilter;

//     @MockBean
//     private JsonFileReader jsonFileReader;

//     private String validJwtToken = "valid-token";

//     @BeforeEach
//     public void setup() {
//     }

//     @Test
//     public void testGetLevelData_success() throws Exception {
//         LevelData mockLevelData = new LevelData();
//         when(jsonFileReader.readLevelDataFromFile("level1.json")).thenReturn(mockLevelData);

//         mockMvc.perform(MockMvcRequestBuilders.get("/levelData")
//                         .param("level", "level1.json"))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.yourField").value("yourValue")); // Adjust jsonPath according to your LevelData fields
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
