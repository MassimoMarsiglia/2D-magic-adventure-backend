package com.example.demo.LevelData;

import java.util.List;

public class LevelData {
    private List<Tile> tiles;

    // Constructors, getters, and setters
    public LevelData() {}

    public LevelData(List<Tile> tiles) {
        this.tiles = tiles;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }
}
