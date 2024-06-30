package com.example.demo.LevelData;

public class SpawnableTile {

    private Position position;

    public SpawnableTile() {}

    public SpawnableTile(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
    
}
