package com.example.demo.Playerdata;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerDataRepository extends JpaRepository<PlayerData, Integer>{
    
    Optional <PlayerData> findByUser_Id(Integer id);

}