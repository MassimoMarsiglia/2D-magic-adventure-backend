package com.example.demo.user.UserKeybinds;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.example.demo.user.User;

import jakarta.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserKeybinds {

    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private String moveUp;

    private String moveDown;

    private String moveLeft;

    private String moveRight;

    private String shoot;

    private String settings;

    // Add more keybind fields as needed

}
