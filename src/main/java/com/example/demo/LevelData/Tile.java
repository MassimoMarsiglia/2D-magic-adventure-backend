package com.example.demo.LevelData;

public class Tile {
    private String imagePath;
    private Position position;
    private boolean collision;

    // Constructors, getters, and setters
    public Tile() {}

    public Tile(String imagePath, Position position, boolean collision) {
        this.imagePath = imagePath;
        this.position = position;
        this.collision = collision;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }
}
