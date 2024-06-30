package com.example.demo.LevelData;

public class Objects { 

    private String type;

    private Position position;

    private Boolean collected;

    private String nextLevel;
    
    public Objects() {
    }

    public Objects(String type, Position position, Boolean collected, String nextLevel) {
        this.type = type;
        this.position = position;
        this.collected = collected;
        this.nextLevel = nextLevel;
    }

    public String getType() {
        return this.type;
    }

    public String getNextLevel() {
        return this.nextLevel;
    }

    public void setNextLevel(String nextLevel) {
        this.nextLevel = nextLevel;
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
