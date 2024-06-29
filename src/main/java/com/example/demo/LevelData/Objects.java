package com.example.demo.LevelData;

public class Objects { 

    private String type;

    private Position position;

    private Boolean collected;
    
    public Objects() {
    }

    public Objects(String type, Position position, Boolean collected) {
        this.type = type;
        this.position = position;
        this.collected = collected;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Boolean isCollected() {
        return this.collected;
    }

    public Boolean getCollected() {
        return this.collected;
    }

    public void setCollected(Boolean collected) {
        this.collected = collected;
    }

    
}
