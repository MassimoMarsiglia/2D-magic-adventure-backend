package com.example.demo.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveAndFindById() {
        // Given
        User user = new User();
        user.setUsername("testuser");
        user = userRepository.save(user); // Save the user

        // When
        Optional<User> foundUser = userRepository.findById(user.getId());

        // Then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("testuser");
    }

    @Test
    public void testFindByUsername() {
        // Given
        User user = new User();
        user.setUsername("testuser");
        userRepository.save(user);

        // When
        User foundUser = userRepository.findByUsername("testuser").get();

        // Then
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo("testuser");
    }

    @Test
    public void testDelete() {
        // Given
        User user = new User();
        user.setUsername("testuser");
        user = userRepository.save(user);

        // When
        userRepository.delete(user);
        Optional<User> foundUser = userRepository.findById(user.getId());

        // Then
        assertThat(foundUser).isNotPresent();
    }
}
