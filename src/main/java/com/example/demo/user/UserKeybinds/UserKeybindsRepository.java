package com.example.demo.user.UserKeybinds;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserKeybindsRepository extends JpaRepository<UserKeybinds, Integer>{
    
    Optional <UserKeybinds> findByUser_Id(Integer id);

}
