package com.example.demo.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.ActiveProfiles;

import com.example.demo.user.UserKeybinds.UserKeybinds;
import com.example.demo.user.UserKeybinds.UserKeybindsRepository;

@DataJpaTest
// @AutoConfigureTestDatabase(replace = Replace.NONE) // Use real database if necessary or H2 by default
@AutoConfigureMockMvc
@ActiveProfiles("test") // Use the test profile
public class UserKeybindsRepositoryTest {

    @Autowired
    private UserKeybindsRepository userKeybindsRepository;

    @Autowired
    private UserRepository userRepository; // Assuming you have a UserRepository for User entity

    @Test
    public void testSaveAndFindById() {
        // Given
        User user = new User();
        user.setUsername("testuser");
        user = userRepository.save(user); // Save the user first

        UserKeybinds keybinds = new UserKeybinds();
        keybinds.setUser(user);
        keybinds = userKeybindsRepository.save(keybinds);

        // When
        Optional<UserKeybinds> foundKeybinds = userKeybindsRepository.findById(keybinds.getId());

        // Then
        assertThat(foundKeybinds).isPresent();
        assertThat(foundKeybinds.get().getUser().getUsername()).isEqualTo("testuser");
    }

    @Test
    public void testFindByUserId() {
        // Given
        User user = new User();
        user.setUsername("testuser");
        user = userRepository.save(user); // Save the user first

        UserKeybinds keybinds = new UserKeybinds();
        keybinds.setUser(user);
        userKeybindsRepository.save(keybinds);

        // When
        Optional<UserKeybinds> foundKeybinds = userKeybindsRepository.findByUser_Id(user.getId());

        // Then
        assertThat(foundKeybinds).isPresent();
        assertThat(foundKeybinds.get().getUser().getId()).isEqualTo(user.getId());
    }

    @Test
    public void testDelete() {
        // Given
        User user = new User();
        user.setUsername("testuser");
        user = userRepository.save(user); // Save the user first

        UserKeybinds keybinds = new UserKeybinds();
        keybinds.setUser(user);
        keybinds = userKeybindsRepository.save(keybinds);

        // When
        userKeybindsRepository.delete(keybinds);
        Optional<UserKeybinds> foundKeybinds = userKeybindsRepository.findById(keybinds.getId());

        // Then
        assertThat(foundKeybinds).isNotPresent();
    }
}
