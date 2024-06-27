package com.example.demo.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserKeybindsRequest {

    private String moveUp;

    private String moveDown;

    private String moveLeft;

    private String moveRight;
    
    private String shoot;

    private String settings;

}
