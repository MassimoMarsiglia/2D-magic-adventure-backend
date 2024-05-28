package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.LevelData.*;
import java.io.IOException;

@RestController
public class MyController {

    private final JsonFileReader jsonFileReader;

    @Autowired
    public MyController(JsonFileReader jsonFileReader) {
        this.jsonFileReader = jsonFileReader;
    }

    @GetMapping("/levelData")
    public ResponseEntity<LevelData> getLevelData(@RequestParam("level") String fileName) {
        try {
            LevelData levelData = jsonFileReader.readLevelDataFromFile(fileName);
            return ResponseEntity.ok(levelData);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
