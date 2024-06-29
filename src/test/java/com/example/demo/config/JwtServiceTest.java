// package com.example.demo.config;

// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.ClaimsBuilder;
// import io.jsonwebtoken.ExpiredJwtException;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.io.Decoders;
// import io.jsonwebtoken.security.Keys;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.test.context.junit.jupiter.SpringExtension;

// import java.lang.reflect.InvocationTargetException;
// import java.lang.reflect.Method;
// import java.time.Clock;
// import java.time.Instant;
// import java.time.ZoneId;
// import java.util.Collections;
// import java.util.Date;
// import java.util.HashMap;
// import java.util.Map;

// import javax.crypto.SecretKey;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.when;

// @ExtendWith(SpringExtension.class)
// @SpringBootTest
// @AutoConfigureMockMvc
// public class JwtServiceTest {

//     @Value("${SECRET_KEY}")
//     private String SECRET_KEY;

//     @Autowired
//     private JwtService jwtService;

//     @Autowired
//     private PasswordEncoder passwordEncoder; // Assuming you have this bean configured

//     private UserDetails userDetails;

//     private UserDetails userDetails2;

//     @BeforeEach
//     public void setup() {
//         // Mock UserDetails
//         userDetails = new User("username1", "password", Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
//         userDetails2 = new User("username2", "password", Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
//     }

//     @Test
//     public void testGenerateToken() {
//         String token = jwtService.generateToken(userDetails);

//         assertNotNull(token);
//         assertTrue(token.length() > 0);
//     }

//     @Test
//     public void testExtractEmailFromToken_success() {
//         String token = generateSampleToken(userDetails.getUsername());

//         String extractedEmail = jwtService.extractEmail(token);

//         assertEquals(userDetails.getUsername(), extractedEmail);
//     }

//     @Test
//     public void testIsTokenValid() {
//         String token = generateSampleToken(userDetails.getUsername());

//         boolean isValid = jwtService.isTokenValid(token, userDetails);

//         assertTrue(isValid);
//     }

//     @Test 
//     public void isTokenInValidForUser() { //token does not belong to the right user
//         String invalidTokenForUser = jwtService.generateToken(userDetails);
//         boolean isFalse = jwtService.isTokenValid(invalidTokenForUser, userDetails2);

//         assertFalse(isFalse);
//     }

//     @Test
//     public void testIsTokenInvalid_Expired() {

//         String expiredToken = generateSampleExpiredToken(userDetails.getUsername());

//         assertThrows(ExpiredJwtException.class, () -> {
//         jwtService.isTokenValid(expiredToken, userDetails);
//         });
//     }

//     // Helper method to generate a sample JWT token
//     private String generateSampleToken(String subject) {
//         return Jwts.builder()
//                 .subject(subject)
//                 .issuedAt(new Date())
//                 .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
//                 .signWith(getSignInKey())
//                 .compact();
//     }

//     private String generateSampleExpiredToken(String subject) {

//         return Jwts.builder()
//                 .subject(subject)
//                 .issuedAt(new Date(System.currentTimeMillis()-200))
//                 .expiration(new Date(System.currentTimeMillis()-100)) // already expired
//                 .signWith(getSignInKey())
//                 .compact();
//     }

//     private SecretKey getSignInKey() {
//         byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
//         return Keys.hmacShaKeyFor(keyBytes);
//     }
// }
