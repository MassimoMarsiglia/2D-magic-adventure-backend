package com.example.demo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import com.example.demo.LevelData.*;

import java.io.File;
import java.io.IOException;


@Service
public class JsonFileReader {

    private final ObjectMapper objectMapper;

    public JsonFileReader(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public LevelData readLevelDataFromFile(String fileName) throws IOException {
        File file = new File("src/main/resources/" + fileName);
        return objectMapper.readValue(file, LevelData.class);
    }
}
