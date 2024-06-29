package com.example.demo.LevelData;

import java.util.List;

public class LevelData {
    private List<Tile> tiles;
    private List<Objects> objects;

    // Constructors, getters, and setters
    public LevelData() {}

    public LevelData(List<Tile> tiles, List<Objects> objects) {
        this.tiles = tiles;
        this.objects = objects;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }

    public List<Objects> getObjects() {
        return objects;
    }

    public void setObjects(List<Objects> objects) {
        this.objects = objects;
    }
}
