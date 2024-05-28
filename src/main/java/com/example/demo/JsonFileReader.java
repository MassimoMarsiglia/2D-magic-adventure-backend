package com.example.demo;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import com.example.demo.LevelData.*;

import java.io.IOException;


@Service
public class JsonFileReader {

    private final ObjectMapper objectMapper;

    public JsonFileReader(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public LevelData readLevelDataFromFile(String fileName) throws IOException {
        // Use ClassPathResource to load files from the classpath
        ClassPathResource resource = new ClassPathResource(fileName);
        return objectMapper.readValue(resource.getInputStream(), LevelData.class);
    }
}